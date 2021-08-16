package com.ipusoft.context.manager;

import android.os.Build;
import android.util.Log;

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
        Log.d(TAG, "isOPPO: -------》" + brand);
        return StringUtils.equals("oppo", brand);
    }
}
