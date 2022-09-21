package com.ipusoft.context;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;

import java.util.List;

/**
 * author : GWFan
 * time   : 4/15/21 9:08 AM
 * desc   : Service 管理类
 */

public class ServiceManager {

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
}
