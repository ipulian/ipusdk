package com.ipusoft.utils;

import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;

import com.ipusoft.context.AppContext;

import java.util.Locale;

/**
 * author : GWFan
 * time   : 8/2/21 4:07 PM
 * desc   : 设备相关工具类
 */

public class DeviceUtils {

    private static Vibrator vibrator;

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机设备名
     *
     * @return 手机设备名
     */
    public static String getSystemDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机主板名
     *
     * @return 主板名
     */
    public static String getDeviceBoard() {
        return Build.BOARD;
    }


    /**
     * 获取手机厂商名
     *
     * @return 手机厂商名
     */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    private static Vibrator getVibrator() {
        if (vibrator == null) {
            vibrator = (Vibrator) AppContext.getAppContext().getSystemService(Context.VIBRATOR_SERVICE);
        }
        return vibrator;
    }

    public static void vibrate(final long milliseconds) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) return;
        vibrator.vibrate(milliseconds);
    }

    public static String getSerialNumber() {
        return Settings.Secure.getString(AppContext.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
