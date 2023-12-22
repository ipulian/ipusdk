package com.ipusoft.context;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elvishew.xlog.XLog;
import com.ipusoft.utils.ExceptionUtils;

import java.util.List;

/**
 * author : GWFan
 * time   : 4/15/21 9:08 AM
 * desc   : Service 管理类
 */

public class ServiceManager {
    private static final String TAG = "ServiceManager";

    /**
     * 判断服务是否运行
     */
    public static boolean isServiceRunning(Class<? extends Service> clazz) {
        ActivityManager activityManager = (ActivityManager) AppContext.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            if (clazz.getName().equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }

    /**
     * 启动PushCoreService
     */
    public static void startPushCoreService() {
        try {
            boolean appRunningForeground = AppManager.isRunningForeground(AppContext.getAppContext());
            Log.d(TAG, "startPushCoreService: --------" + appRunningForeground);
            if (appRunningForeground) {
                Intent intent = new Intent(AppContext.getAppContext(), PushCoreService.class);
                Log.d(TAG, "startPushCoreService: --------2");
                AppContext.getAppContext().startService(intent);
                Log.d(TAG, "startPushCoreService: --------3");
            }
        } catch (Exception e) {
            e.printStackTrace();
            XLog.d(TAG + "startPushCoreService" + ExceptionUtils.getErrorInfo(e));
        }
    }
}
