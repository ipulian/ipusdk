package com.ipusoft.context;

import android.app.Application;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.db.DBManager;
import com.ipusoft.context.init.SDKCommonInit;
import com.ipusoft.context.listener.IPhoneStateListener;
import com.ipusoft.context.listener.OnPhoneStateChangedListener;
import com.tencent.mmkv.MMKV;

/**
 * author : GWFan
 * time   : 5/1/21 9:46 AM
 * desc   :
 */

public abstract class IpuSoftSDK extends Application implements IBaseApplication {
    private static final String TAG = "IpuSoftSDK";
    private static Application mApp;
    /**
     * 调用者的身份信息
     */
    private static IAuthInfo iAuthInfo;
    /**
     * 根据身份信息生成的Token
     */
    private static String token;

    public static Application getAppContext() {
        return mApp;
    }

    public static void setToken(String token) {
        IpuSoftSDK.token = token;
    }

    public static String getToken() {
        return IpuSoftSDK.token;
    }

    public static void init(Application mApp) {
        IpuSoftSDK.mApp = mApp;

        /*
         * 初始化MMKV
         */
        MMKV.initialize(mApp);
        /*
         * 初始化ARouter
         */
        initARouter();
        /*
         * 注册Activity声明周期
         */
        mApp.registerActivityLifecycleCallbacks(new IActivityLifecycle());

        /**
         * 初始化SDK数据库
         */
        DBManager.initDataBase();
        /*
         * 初始化组件
         */
        initILibrary();
    }

    public static String getAuthCode() {
        return SDKCommonInit.generateAuthCode();
    }

    public static IAuthInfo getAuthInfo() {
        return iAuthInfo;
    }

    public static void init(Application mApp, IAuthInfo iAuthInfo) throws RuntimeException {
        if (iAuthInfo != null) {
            IpuSoftSDK.iAuthInfo = iAuthInfo;
            SDKCommonInit.initSDKToken(iAuthInfo);
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
            SDKCommonInit.initSDKToken(iAuthInfo);
        } else {
            Log.d(TAG, "updateAuthInfo: 更新认证信息失败,IAuthInfo = null");
        }
    }

    public static void reLogin(OnSDKLoginListener loginListener) {
        SDKCommonInit.initSDKToken(iAuthInfo, loginListener);
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
                Class<?> clazz = Class.forName(module);
                IBaseApplication baseApplication = (IBaseApplication) clazz.newInstance();
                baseApplication.initModule();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
