package com.ipusoft.context;

import android.app.Application;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.config.Env;
import com.ipusoft.context.http.manager.OpenRetrofitManager;
import com.ipusoft.context.init.SDKCommonInit;

/**
 * author : GWFan
 * time   : 6/1/21 10:29 AM
 * desc   : 保存身份信息和环境信息
 */

public abstract class AppContext extends Application {
    private static final String TAG = "AppContext";
    /**
     * Application
     */
    protected static Application mApp;
    /**
     * SDK运行环境
     */
    protected static Env env;
    /**
     * 调用者的身份信息
     */
    protected static IAuthInfo iAuthInfo;
    /**
     * 根据身份信息生成的Token
     */
    protected static String token = "";

    /**
     * 初始化运行时环境
     */
    protected static void initRuntimeEnvironment() {
        Log.d(TAG, "initRuntimeEnvironment: -------》" + env);
        OpenRetrofitManager.getInstance().initRetrofit();
    }

    protected static void setAppContext(Application mApp) {
        AppContext.mApp = mApp;
    }

    public static Application getAppContext() {
        return mApp;
    }

    public static AppCompatActivity getActivityContext() {
        return IActivityLifecycle.getCurrentActivity();
    }

    protected static void setRuntimeEnv(Env env) {
        Log.d(TAG, "setRuntimeEnv: ------" + env);
        AppContext.env = env;
    }

    public static Env getRuntimeEnv() {
        Log.d(TAG, "getRuntimeEnv: ------？" + AppContext.env);
        return AppContext.env;
    }

    public static void setToken(String token) {
        IpuSoftSDK.token = token;
    }

    public static String getToken() {
        return IpuSoftSDK.token;
    }

    public static String getAuthCode() {
        return SDKCommonInit.generateAuthCode();
    }

    public static IAuthInfo getAuthInfo() {
        return iAuthInfo;
    }
}
