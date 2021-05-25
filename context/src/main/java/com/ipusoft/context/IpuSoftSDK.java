package com.ipusoft.context;

import android.app.Application;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.listener.IPhoneStateListener;
import com.ipusoft.context.listener.OnPhoneStateChangedListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author : GWFan
 * time   : 5/1/21 9:46 AM
 * desc   :
 */

public abstract class IpuSoftSDK extends Application implements IBaseApplication {
    private static final String TAG = "IpuSoftSDK";
    private static Application mApp;
    private static IAuthInfo iAuthInfo;

    public static Application getAppContext() {
        return mApp;
    }

    public static void init(Application mApp) {
        IpuSoftSDK.mApp = mApp;
        /*
         * 初始化ARouter
         */
        initARouter();
        /*
         * 注册Activity声明周期
         */
        mApp.registerActivityLifecycleCallbacks(new IActivityLifecycle());
        /*
         * 初始化组件
         */
        initILibrary();
    }

    public static void init(Application mApp, IAuthInfo iAuthInfo) throws RuntimeException {
        if (iAuthInfo != null) {
            try {
                Class.forName(ModuleRegister.X_LIBRARY);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("初始化失败，未找到xModule");
            }
            IpuSoftSDK.iAuthInfo = iAuthInfo;
        }
        init(mApp);
    }

    /**
     * 注册通话状态的listener
     *
     * @param listener
     */
    public static void setOnPhoneStatusChangedListener(OnPhoneStateChangedListener listener) {
        if (mApp != null) {
            IPhoneStateListener.getInstance().registerPhoneListener(mApp, listener);
        } else {
            Log.d(TAG, "setOnPhoneStatusChangedListener: 注册通话状态listener失败,未初始化SDk");
        }
    }

    /**
     * 更新认证信息
     *
     * @param iAuthInfo
     */
    public static void updateAuthInfo(IAuthInfo iAuthInfo) {
        if (iAuthInfo != null) {
            IpuSoftSDK.iAuthInfo = iAuthInfo;
            initXModule();
        } else {
            Log.d(TAG, "updateAuthInfo: 更新认证信息失败,IAuthInfo = null");
        }
    }

    private static void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(mApp);
    }

    /**
     * 初始化各个组件
     */
    private static void initILibrary() {
        for (String module : ModuleRegister.modules) {
            try {
                if (ModuleRegister.X_LIBRARY.equals(module)) {
                    initXModule();
                } else {
                    Class<?> clazz = Class.forName(module);
                    IBaseApplication baseApplication = (IBaseApplication) clazz.newInstance();
                    baseApplication.initModule();
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initXModule() {
        if (iAuthInfo != null) {
            try {
                Class<?> clazz = Class.forName(ModuleRegister.X_LIBRARY);
                Method initMethod = clazz.getDeclaredMethod("initXModule", IAuthInfo.class);
                initMethod.setAccessible(true);
                initMethod.invoke(null, iAuthInfo);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
