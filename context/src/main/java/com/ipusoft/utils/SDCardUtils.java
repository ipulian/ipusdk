package com.ipusoft.utils;

import android.os.Environment;

/**
 * author : GWFan
 * time   : 5/2/21 5:42 PM
 * desc   :
 */

public class SDCardUtils {
    public static boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
