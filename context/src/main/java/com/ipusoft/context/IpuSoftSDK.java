package com.ipusoft.context;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.context.bean.SeatInfo;
import com.ipusoft.context.cache.AppCacheContext;
import com.ipusoft.context.constant.CallTypeConfig;
import com.ipusoft.context.db.AppDBManager;
import com.ipusoft.context.iface.BaseSipStatusChangedListener;
import com.ipusoft.context.init.SDKCommonInit;
import com.ipusoft.context.listener.IPhoneStateListener;
import com.ipusoft.context.listener.OnPhoneStateChangedListener;
import com.ipusoft.context.registers.ModuleRegister;
import com.ipusoft.http.QuerySeatInfoHttp;
import com.ipusoft.logger.XLogger;
import com.ipusoft.mmkv.AccountMMKV;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;
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

    public static void init(Application mApp, String env) {

        setAppContext(mApp);

        /*
         * 注册Activity生命周期
         */
        mApp.registerActivityLifecycleCallbacks(new IActivityLifecycle());

        /*
         * 初始化MMKV
         */
        MMKV.initialize(mApp);
        /*
         * 初始化日志系统
         */
        XLogger.initXLog();

        setRuntimeEnv(env);

        initHttpEnvironment();

        /*
         * 初始化ARouter
         */
        // initARouter();

        /*
         * 初始化数据库
         */
        AppDBManager.initDataBase();
        /*
         * 初始化组件
         */
        initIModule();
    }

    /**
     * 外部认证信息，由SDK生成token
     *
     * @param mApp
     * @param authInfo
     * @throws RuntimeException
     */
    public static void init(Application mApp, String env, AuthInfo authInfo) throws RuntimeException {
        init(mApp, env);
        updateAuthInfo(authInfo);
    }

    /**
     * SIP SDK退出登录
     */
    public static void signOut() {
        CommonDataRepo.setSipSDKSignOut(true);
    }

    /**
     * 更新外部认证信息
     *
     * @param authInfo
     */
    public static void updateAuthInfo(AuthInfo authInfo) {
        if (authInfo != null) {
            AuthInfo oldAuthInfo = AppContext.getAuthInfo();
            if (oldAuthInfo != null && !authInfo.equals(oldAuthInfo)) {
                /*
                 * 认证信息和上次不一样，清空上一个认证信息的相关数据
                 * 重新初始化
                 */
                unInitIModule();
                AppContext.setAuthInfo(authInfo);
                init(mApp, env);
            }
            XLog.d(TAG, "updateAuthInfo: -------->" + GsonUtils.toJson(authInfo));

            String password = authInfo.getPassword();
            if (StringUtils.isNotEmpty(password)) {//SIP SDK的单独拿出来处理
                SeatInfo seatInfo = new SeatInfo(authInfo.getUsername(), authInfo.getKey(), authInfo.getSecret(), authInfo.getPassword());
                CommonDataRepo.setSeatInfo(seatInfo);

                CommonDataRepo.setLocalCallType(CallTypeConfig.SIP.getType());
                CommonDataRepo.setSipSDKSignOut(false);

                registerSip(seatInfo);
                registerSipListener();

            } else {

                SDKCommonInit.initSDKToken(authInfo, status -> {
                    if (OnSDKLoginListener.LoginStatus.SUCCESS == status) {
                        querySeatInfoAndRegisterSIP();
                    }
                });
            }
        } else {
            XLog.d(TAG, "updateAuthInfo: 更新认证信息失败,IAuthInfo = null");
            throw new RuntimeException("更新认证信息失败,IAuthInfo = null");
        }
    }

    /**
     * 查询坐席信息，并注册SIP
     */
    private static long timestamp = 0;

    public static void querySeatInfoAndRegisterSIP() {
        long l = System.currentTimeMillis();
        if (l - timestamp < 3 * 1000) {
            return;
        }
        timestamp = l;
        QuerySeatInfoHttp.querySeatInfo((seatInfo, localCallType) -> {
            CommonDataRepo.setSeatInfo(seatInfo);
            //Log.d(TAG, "querySeatInfoAndRegisterSIP: ---------->" + GsonUtils.toJson(localCallType));
//            if (StringUtils.equals(CallTypeConfig.SIP.getType(), localCallType)) {
            String callType = seatInfo.getCallType();
            Log.d(TAG, "querySeatInfoAndRegisterSIP: _---------->" + callType);
            if (StringUtils.isNotEmpty(callType)) {//呼叫方式中只要有SIP就注册
                if (callType.contains(CallTypeConfig.SIP.getType())) {
                    registerSip(seatInfo);
                    registerSipListener();
                }
            }
            // }
        });
    }

    /**
     * 注册SIP服务
     *
     * @param seatInfo
     */
    private static void registerSip(SeatInfo seatInfo) {
        if (seatInfo != null) {
            try {
                Class<?> clazz = Class.forName(ModuleRegister.SIP_MODULE);
                Method initMethod = clazz.getDeclaredMethod("registerSipService", SeatInfo.class);
                initMethod.setAccessible(true);
                initMethod.invoke(clazz.newInstance(), seatInfo);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注册通话状态的listener
     *
     * @param listener
     */
    public static void registerPhoneStatusChangedListener(OnPhoneStateChangedListener... listener) {
        if (mApp != null) {
            IPhoneStateListener.getInstance().registerPhoneListener(mApp, listener);
        } else {
            XLog.d(TAG, "setOnPhoneStatusChangedListener: 注册通话状态listener失败,未初始化SDk");
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
        boolean flag = false;
        for (int i = 0; i < sipStatusChangedListenerList.size(); i++) {
            if (sipListener.getClass().getName().equals(sipStatusChangedListenerList.get(i).getClass().getName())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            sipStatusChangedListenerList.add(sipListener);
        }
        registerSipListener();
    }

    /**
     * 重新登陆
     *
     * @param loginListener
     */
    public static void reLogin(OnSDKLoginListener loginListener) {
        AuthInfo authInfo = getAuthInfo();
        if (authInfo == null) {
            Toast.makeText(mApp, "登陆失败，认证信息为空", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "reLogin: ->" + "登陆失败，认证信息为空：");
            return;
        }
        SDKCommonInit.initSDKToken(authInfo, loginListener);
    }

//    /**
//     * 初始化ARouter
//     */
//    private static void initARouter() {
//        if (BuildConfig.DEBUG) {
//            ARouter.openLog();
//            ARouter.openDebug();
//        }
//        ARouter.init(mApp);
//    }

    /**
     * 初始化各个组件
     */
    private static void initIModule() {
        for (String module : ModuleRegister.modules) {
            try {
                Class<?> clazz = Class.forName(module);
                IBaseApplication baseApplication = (IBaseApplication) clazz.newInstance();
                baseApplication.initModule();
                if (StringUtils.equals(ModuleRegister.SIP_MODULE, module)) {
                    registerSipListener();
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反初始化各个组件
     */
    public static void unInitIModule() {

        AppDBManager.clearAllTables();
        CommonDataRepo.clearAllData();

        AccountMMKV.clearAll();

        for (String module : ModuleRegister.modules) {
            try {
                Class<?> clazz = Class.forName(module);
                IBaseApplication baseApplication = (IBaseApplication) clazz.newInstance();
                baseApplication.unInitModule();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化SIP状态listener
     */
    private static void registerSipListener() {
        if (ArrayUtils.isNotEmpty(sipStatusChangedListenerList)) {
            try {
                Class<?> clazz = Class.forName(ModuleRegister.SIP_MODULE);
                Method initMethod = clazz.getDeclaredMethod("registerSipListener", List.class);
                initMethod.setAccessible(true);
                initMethod.invoke(clazz.newInstance(), sipStatusChangedListenerList);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
