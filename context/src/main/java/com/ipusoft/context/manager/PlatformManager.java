package com.ipusoft.context.manager;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.component.ToastUtils;
import com.ipusoft.utils.StringUtils;

/**
 * author : GWFan
 * time   : 1/28/21 9:41 AM
 * desc   :
 */

public class PlatformManager {
    private static final String TAG = "PlatformManager";

    /**
     * 是否是MIUI
     *
     * @return
     */
    public static boolean isMIUI() {
        String brand = Build.BRAND.toLowerCase();
        return StringUtils.equals("xiaomi", brand) || StringUtils.equals("redmi", brand);
    }

    public static boolean isHUAWEI() {
        String brand = Build.BRAND.toLowerCase();
        return StringUtils.equals("huawei", brand) || StringUtils.equals("honor", brand);
    }

    public static boolean isOPPO() {
        String brand = Build.BRAND.toLowerCase();
        return StringUtils.equals("oppo", brand);
    }

    /**
     * 跳转到小米开启通话自动录音功能页面
     */
    public static void startXiaomiRecord() {
        try {
            ComponentName componentName = new ComponentName("com.android.phone", "com.android.phone.settings.CallRecordSetting");
            Intent intent = new Intent();
            intent.setComponent(componentName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppContext.getAppContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                AppContext.getActivityContext().runOnUiThread(() -> ToastUtils.showMessage("跳转失败，请手动打开"));
            } catch (Exception exception) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转到华为开启通话自动录音功能页面
     */
    public static void startHuaweiRecord() {
        try {
            ComponentName componentName = new ComponentName("com.android.phone", "com.android.phone.MSimCallFeaturesSetting");
            Intent intent = new Intent();
            intent.setComponent(componentName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppContext.getAppContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                AppContext.getActivityContext().runOnUiThread(() -> ToastUtils.showMessage("跳转失败，请手动打开"));
            } catch (Exception exception) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转到OPPO开启通话自动录音功能页面
     */
    public static void startOppoRecord() {
        try {
            ComponentName componentName = new ComponentName("com.android.phone", "com.android.phone.OppoCallFeaturesSetting");
            Intent intent = new Intent();
            intent.setComponent(componentName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppContext.getAppContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                AppContext.getActivityContext().runOnUiThread(() -> ToastUtils.showMessage("跳转失败，请手动打开"));
            } catch (Exception exception) {
                e.printStackTrace();
            }
        }
    }
}
