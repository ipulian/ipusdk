package com.ipusoft.context;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.cache.AppCacheContext;
import com.ipusoft.context.config.Env;
import com.ipusoft.context.db.DBManager;
import com.ipusoft.context.iface.BaseSipStatusChangedListener;
import com.ipusoft.context.init.SDKCommonInit;
import com.ipusoft.context.listener.IPhoneStateListener;
import com.ipusoft.context.listener.OnPhoneStateChangedListener;
import com.ipusoft.context.utils.GsonUtils;
import com.ipusoft.context.utils.StringUtils;
import com.tencent.mmkv.MMKV;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * author : GWFan
 * time   : 5/1/21 9:46 AM
 * desc   :
 */

public abstract class IpuSoftSDK extends AppCacheContext implements IBaseApplication {
    private static final String TAG = "IpuSoftSDK";
    private static List<BaseSipStatusChangedListener> sipStatusChangedListenerList;

    public static void init(Application mApp, Env env) {

        setAppContext(mApp);

        setRuntimeEnv(env);

        initRuntimeEnvironment();

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
        initIModule();
    }

    /**
     * 传认证信息，由SDK生成token
     *
     * @param mApp
     * @param iAuthInfo
     * @throws RuntimeException
     */
    public static void init(Application mApp, Env env, IAuthInfo iAuthInfo) throws RuntimeException {
        if (iAuthInfo != null) {
            AppContext.iAuthInfo = iAuthInfo;
            SDKCommonInit.initSDKToken(iAuthInfo);
        }

        init(mApp, env);
    }

    /**
     * 直接传token
     *
     * @param mApp
     * @param token
     * @throws RuntimeException
     */
    public static void init(Application mApp, Env env, String token) throws RuntimeException {
        if (StringUtils.isNotEmpty(token)) {
            IpuSoftSDK.setToken(token);
        }
        init(mApp, env);
    }

    /**
     * 注册通话状态的listener
     *
     * @param listener
     */
    public static void registerPhoneStatusChangedListener(OnPhoneStateChangedListener listener) {
        if (mApp != null) {
            IPhoneStateListener.getInstance().registerPhoneListener(mApp, listener);
        } else {
            Log.d(TAG, "setOnPhoneStatusChangedListener: 注册通话状态listener失败,未初始化SDk");
        }
    }

    /**
     * 注册Sip状态的listener
     *
     * @param sipListener
     */
    public static void registerSipStatusChangedListener(BaseSipStatusChangedListener sipListener) {
        if (sipStatusChangedListenerList == null) {
            sipStatusChangedListenerList = new ArrayList<>();
        }
        sipStatusChangedListenerList.add(sipListener);
    }

    /**
     * 更新认证信息
     *
     * @param iAuthInfo
     */
    public static void updateAuthInfo(IAuthInfo iAuthInfo) {
        if (iAuthInfo != null) {
            AppContext.iAuthInfo = iAuthInfo;
            SDKCommonInit.initSDKToken(iAuthInfo);
        } else {
            Log.d(TAG, "updateAuthInfo: 更新认证信息失败,IAuthInfo = null");
        }
    }

    public static void updateToken(String token) {
        IpuSoftSDK.setToken(token);
    }

    public static void reLogin(OnSDKLoginListener loginListener) {
        if (iAuthInfo == null) {
            Toast.makeText(mApp, "登陆失败，认证信息为空", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "reLogin: ->" + "登陆失败，认证信息为空：" + GsonUtils.toJson(iAuthInfo));
            return;
        }
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
    private static void initIModule() {
        for (String module : ModuleRegister.modules) {
            if (StringUtils.equals(ModuleRegister.SIP_MODULE, module)) {
                initSipModule();
            } else {
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

    /**
     * 初始化SIP组件
     */
    private static void initSipModule() {
        if (iAuthInfo != null) {
            try {
                Class<?> clazz = Class.forName(ModuleRegister.SIP_MODULE);
                Method initMethod = clazz.getDeclaredMethod("initSipModule");
                initMethod.setAccessible(true);
                initMethod.invoke(null, sipStatusChangedListenerList);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
