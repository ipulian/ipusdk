package com.ipusoft.context;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.init.SDKCommonInit;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.utils.ExceptionUtils;

/**
 * author : GWFan
 * time   : 6/1/21 10:29 AM
 * desc   : 保存身份信息和环境信息
 */

public class AppContext extends Application {
    private static final String TAG = "AppContext";
    /**
     * Application
     */
    protected static Application mApp;

    private static AppContext appContext;

    public static int appType = 1;

    //是否打开了录音开关
    public static boolean appHasRecordingPermission = false;

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

    private static final Object object = new Object();

    public static AppContext getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
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
        synchronized (object) {
            CommonDataRepo.setToken(token);
        }
    }

    /**
     * @return Token(通用)
     */
    public static String getToken() {
        synchronized (object) {
            String token;
            try {
                token = CommonDataRepo.getToken();
            } catch (Exception e) {
                token = "";
                XLog.e(TAG + "->getToken->" + ExceptionUtils.getErrorInfo(e));
            }
            return token;
        }
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
        CommonDataRepo.setAuthInfo(authInfo);
    }

    public static AuthInfo getAuthInfo() {
        return CommonDataRepo.getAuthInfo();
    }

    /**
     * 内部认证信息
     *
     * @param iAuthInfo
     */
    public static void setIAuthInfo(IAuthInfo iAuthInfo) {
        if (iAuthInfo != null) {
            CommonDataRepo.setIAuthInfo(iAuthInfo);
            AppContext.setToken(iAuthInfo.getToken());
        }
    }

    public static IAuthInfo getIAuthInfo() {
        return CommonDataRepo.getIAuthInfo();
    }

    public static String getUid() {
        return CommonDataRepo.getUid();
    }


    public static boolean getAppRecordingPermission() {
        return appHasRecordingPermission;
    }

    public static void setAppRecordingPermission(boolean permission) {
        appHasRecordingPermission = permission;
    }
}