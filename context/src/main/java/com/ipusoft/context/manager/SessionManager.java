package com.ipusoft.context.manager;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.IActivityLifecycle;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.view.dialog.ExpiredDialog;
import com.ipusoft.mmkv.AccountMMKV;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.ExceptionUtils;

import java.util.List;

/**
 * author : GWFan
 * time   : 8/25/20 11:43 AM
 * desc   : App相关工具类
 */

public class SessionManager {
    private static final String TAG = "AppUtils";

    /**
     * 身份过期，退出登陆
     */
    public static void sessionExpired() {
        AppCompatActivity context = AppContext.getActivityContext();

        IpuSoftSDK.unInitIModule();
        AccountMMKV.clearAll();

        ExpiredDialog.getInstance()
                .setOnOkClickListener((v) -> {
                    List<Activity> activityList = IActivityLifecycle.getActivityStack();
                    if (ArrayUtils.isNotEmpty(activityList)) {
                        for (Activity activity : activityList) {
                            activity.finish();
                        }
                    }
                    try {
                        Class<?> clazz = Class.forName("com.ipusoft.lianlian.np.view.activity.login.LoginActivity");
                        context.finish();
                        Intent intent = new Intent(context, clazz);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        Log.d(TAG, "sessionExpired: --------->" + ExceptionUtils.getErrorInfo(e));
                    }
                })
                .show();
    }

    /**
     * 退出登陆
     */
    public static void logOut() {
        AppCompatActivity context = AppContext.getActivityContext();
        IpuSoftSDK.unInitIModule();
        AccountMMKV.clearAll();

        List<Activity> activityList = IActivityLifecycle.getActivityStack();
        if (ArrayUtils.isNotEmpty(activityList)) {
            for (Activity activity : activityList) {
                activity.finish();
            }
        }
        try {
            Class<?> clazz = Class.forName("com.ipusoft.lianlian.np.view.activity.login.LoginActivity");
            context.finish();
            Intent intent = new Intent(context, clazz);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
