package com.ipusoft.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.component.ToastUtils;

/**
 * author : GWFan
 * time   : 1/28/21 9:41 AM
 * desc   :
 */

public class SysRecordingUtils {
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

    public static boolean isVIVO() {
        String brand = Build.BRAND.toLowerCase();
        return StringUtils.equals("vivo", brand);
    }

    public static boolean isRealme() {
        String brand = Build.BRAND.toLowerCase();
        return StringUtils.equals("realme", brand);
    }

    public static boolean isMeizu() {
        String brand = Build.BRAND.toLowerCase();
        return StringUtils.equals("meizu", brand);
    }

    public static boolean isOnePlus() {
        String brand = Build.BRAND.toLowerCase();
        return StringUtils.equals("OnePlus", brand);
    }

    public static boolean isMotorola() {
        String brand = Build.BRAND.toLowerCase();
        return StringUtils.equals("motorola", brand);
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

    /**
     * 跳转到VIVO开启通话自动录音功能页面
     */
    public static void startViVoRecord() {
        try {
            ComponentName componentName = new ComponentName("com.android.incallui", "com.android.incallui.record.CallRecordSetting");
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
     * 检查小米手机自动录音功能是否开启，true已开启  false未开启
     *
     * @return
     */
    public static boolean checkXiaomiRecord() {
        try {
            int key = Settings.System.getInt(AppContext.getAppContext().getContentResolver(), "button_auto_record_call");
            XLog.d(TAG, "Xiaomi key:" + key);
            //0是未开启,1是开启
            return key != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 检查OPPO手机自动录音功能是否开启，true已开启  false未开启
     *
     * @return
     */
    public static boolean checkOppoRecord() {
        try {
            int key = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1 ? Settings.Global.getInt(AppContext.getAppContext().getContentResolver(), "oppo_all_call_audio_record") : 0;
            XLog.d(TAG, "Oppo key:" + key);
            //0代表OPPO自动录音未开启,1代表OPPO自动录音已开启
            return key != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 检查VIVO自动录音功能是否开启，true已开启  false未开启
     *
     * @return
     */
    public static boolean checkViVoRecord() {
        try {
            int key = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1 ? Settings.Global.getInt(AppContext.getAppContext().getContentResolver(), "call_record_state_global") : 0;
            XLog.d(TAG, "Vivo key:" + key);
            //0代表VIVO自动录音未开启,1代表VIVO所有通话自动录音已开启,2代表指定号码自动录音
            return key == 1;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 检查华为手机自动录音功能是否开启，true已开启  false未开启
     *
     * @return
     */
    public static boolean checkHuaweiRecord() {
        try {
            int key = Settings.Secure.getInt(AppContext.getAppContext().getContentResolver(), "enable_record_auto_key");
            XLog.d(TAG, "Huawei key:" + key);
            //0代表华为自动录音未开启,1代表华为自动录音已开启
            return key != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    //Secure
    public static String checkSecureSetting() {
        StringBuilder builder = new StringBuilder("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Cursor cursor = AppContext.getAppContext().getContentResolver().query(Settings.Secure.CONTENT_URI, null, null, null);
            String[] columnNames = cursor.getColumnNames();
            while (cursor.moveToNext()) {
                for (String columnName : columnNames) {
                    String string = cursor.getString(cursor.getColumnIndex(columnName));
                    builder.append(columnName).append(":").append(string).append("\n");
                }
            }
            cursor.close();
            Log.e(TAG, builder.toString());
        }
        return builder.toString();
    }

    //Global
    public static String checkGlobalSetting() {
        StringBuilder builder = new StringBuilder("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Cursor cursor = AppContext.getAppContext().getContentResolver().query(Settings.Global.CONTENT_URI, null, null, null);
            String[] columnNames = cursor.getColumnNames();
            while (cursor.moveToNext()) {
                for (String columnName : columnNames) {
                    String string = cursor.getString(cursor.getColumnIndex(columnName));
                    builder.append(columnName).append(":").append(string).append("\n");
                }
            }
            cursor.close();
            Log.e(TAG, builder.toString());
        }
        return builder.toString();
    }

    //System
    public static String checkSystemSetting() {
        StringBuilder builder = new StringBuilder("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Cursor cursor = AppContext.getAppContext().getContentResolver().query(Settings.System.CONTENT_URI, null, null, null);
            String[] columnNames = cursor.getColumnNames();
            while (cursor.moveToNext()) {
                for (String columnName : columnNames) {
                    String string = cursor.getString(cursor.getColumnIndex(columnName));
                    builder.append(columnName).append(":").append(string).append("\n");
                }
            }
            cursor.close();
            Log.e(TAG, builder.toString());
        }
        return builder.toString();
    }
}
