package com.ipusoft.context;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.ipusoft.context.bean.AuthInfo;
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
     * 外部认证信息
     */
    protected static AuthInfo authInfo;
    /**
     * 内部认证信息
     */
    protected static IAuthInfo iAuthInfo;
    /**
     * 根据身份信息生成的Token(通用)
     */
    protected static String token = "";

    protected static String uid = "";

    /**
     * 初始化运行时环境
     */
    protected static void initRuntimeEnvironment() {
        OpenRetrofitManager.getInstance().initRetrofit();
    }

    /**
     * @param mApp Application
     */
    protected static void setAppContext(Application mApp) {
        AppContext.mApp = mApp;
    }

    /**
     * @return Application
     */
    public static Application getAppContext() {
        return mApp;
    }

    /**
     * @return 返回当前Activity
     */
    public static AppCompatActivity getActivityContext() {
        return IActivityLifecycle.getCurrentActivity();
    }

    /**
     * @param env 设置运行环境
     */
    protected static void setRuntimeEnv(Env env) {
        AppContext.env = env;
    }

    /**
     * @return 运行环境
     */
    public static Env getRuntimeEnv() {
        return AppContext.env;
    }

    /**
     * @param token 设置Token(外部)
     */
    public static void setToken(String token) {
        AppContext.token = token;
    }

    /**
     * @return Token(通用)
     */
    public static String getToken() {
        return AppContext.token;
    }

    /**
     * @return 外部认证code
     */
    public static String getAuthCode() {
        return SDKCommonInit.generateAuthCode();
    }

    /**
     * @return 外部认证信息
     */
    public static AuthInfo getAuthInfo() {
        return authInfo;
    }

    /**
     * @param iAuthInfo 更新内部认证信息
     */
    protected static void updateIAuthInfo(IAuthInfo iAuthInfo) {
        AppContext.iAuthInfo = iAuthInfo;
        AppContext.token = iAuthInfo.getToken();
        AppContext.uid = iAuthInfo.getUid();
    }

    /**
     * @return 返回内部UID
     */
    public static String getUid() {
        return AppContext.uid;
    }
}
