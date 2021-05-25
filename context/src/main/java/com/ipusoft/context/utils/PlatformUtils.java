package com.ipusoft.context.utils;

import android.os.Build;
import android.util.Log;

/**
 * author : GWFan
 * time   : 1/28/21 9:41 AM
 * desc   :
 */

public class PlatformUtils {
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
        Log.d(TAG, "brand: ---------->" + brand);
        return StringUtils.equals("huawei", brand) || StringUtils.equals("honor", brand);
    }
}
