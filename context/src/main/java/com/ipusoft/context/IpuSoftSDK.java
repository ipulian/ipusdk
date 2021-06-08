package com.ipusoft.context;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ipusoft.context.bean.AuthInfo;
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
     * 传外部认证信息，由SDK生成token
     *
     * @param mApp
     * @param authInfo
     * @throws RuntimeException
     */
    public static void init(Application mApp, Env env, AuthInfo authInfo) throws RuntimeException {
        setRuntimeEnv(env);
        if (authInfo != null) {
            AppContext.authInfo = authInfo;
            SDKCommonInit.initSDKToken(authInfo);
        }
        init(mApp, env);
    }

    /**
     * 传内部认证信息
     *
     * @param mApp
     * @param iAuthInfo
     * @throws RuntimeException
     */
    public static void init(Application mApp, Env env, IAuthInfo iAuthInfo) throws RuntimeException {
        setRuntimeEnv(env);
        AppContext.updateIAuthInfo(iAuthInfo);
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
            sipStatusChangedListenerList.add(sipListener);
        } else {
            for (BaseSipStatusChangedListener listener : sipStatusChangedListenerList) {
                if (!StringUtils.equals(sipListener.getClass().getName(), listener.getClass().getName())) {
                    sipStatusChangedListenerList.add(sipListener);
                }
            }
        }
        initSipModule();
    }

    /**
     * 更新外部认证信息
     *
     * @param authInfo
     */
    public static void updateAuthInfo(AuthInfo authInfo) {
        if (authInfo != null) {
            AppContext.authInfo = authInfo;
            SDKCommonInit.initSDKToken(authInfo);
        } else {
            Log.d(TAG, "updateAuthInfo: 更新认证信息失败,IAuthInfo = null");
        }
    }

    /**
     * 更新内部认证信息
     *
     * @param iAuthInfo
     */
    public static void updateIAuthInfo(IAuthInfo iAuthInfo) {
        AppContext.updateIAuthInfo(iAuthInfo);
    }

    /**
     * 重新登陆
     *
     * @param loginListener
     */
    public static void reLogin(OnSDKLoginListener loginListener) {
        if (authInfo == null) {
            Toast.makeText(mApp, "登陆失败，认证信息为空", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "reLogin: ->" + "登陆失败，认证信息为空：" + GsonUtils.toJson(authInfo));
            return;
        }
        SDKCommonInit.initSDKToken(authInfo, loginListener);
    }

    /**
     * 初始化ARouter
     */
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
            try {
                Class<?> clazz = Class.forName(module);
                Log.d(TAG, "initIModule: --------->" + module);
                IBaseApplication baseApplication = (IBaseApplication) clazz.newInstance();
                baseApplication.initModule();
                if (StringUtils.equals(ModuleRegister.SIP_MODULE, module)) {
                    initSipModule();
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                Log.d(TAG, "initIModule: ---------->" + e.toString());
            }
        }
    }

    /**
     * 初始化SIP组件
     */
    private synchronized static void initSipModule() {
        if (authInfo != null || StringUtils.isNotEmpty(token)) {
            try {
                Class<?> clazz = Class.forName(ModuleRegister.SIP_MODULE);
                Method initMethod = clazz.getDeclaredMethod("initSipModule",
                        List.class);
                initMethod.setAccessible(true);
                initMethod.invoke(clazz.newInstance(), sipStatusChangedListenerList);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
