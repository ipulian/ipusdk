package com.ipusoft.context;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.init.SDKCommonInit;
import com.ipusoft.mmkv.datastore.AppDataStore;

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
     * @param token 设置Token(外部)
     */
    public static void setToken(String token) {
        AppDataStore.setToken(token);
    }

    /**
     * @return Token(通用)
     */
    public static String getToken() {
        return AppDataStore.getToken();
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
    public static void setAuthInfo(AuthInfo authInfo) {
        AppDataStore.setAuthInfo(authInfo);
    }

    public static AuthInfo getAuthInfo() {
        return AppDataStore.getAuthInfo();
    }

    /**
     * 内部认证信息
     *
     * @param iAuthInfo
     */
    public static void setIAuthInfo(IAuthInfo iAuthInfo) {
        if (iAuthInfo != null) {
            AppDataStore.setIAuthInfo(iAuthInfo);
            AppContext.setToken(iAuthInfo.getToken());
        }
    }

    public static IAuthInfo getIAuthInfo() {
        return AppDataStore.getIAuthInfo();
    }

    public static String getUid() {
        return AppDataStore.getUid();
    }
}
