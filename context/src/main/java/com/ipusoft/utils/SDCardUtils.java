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

    public static String getExternalStoragePath() {
        // 获取SdCard状态
        String state = android.os.Environment.getExternalStorageState();
        // 判断SdCard是否存在并且是可用的
        if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
            if (android.os.Environment.getExternalStorageDirectory().canWrite()) {
                return android.os.Environment.getExternalStorageDirectory().getPath();
            }
        }
        return null;
    }

    public static long getFreeSpace() {
        return Environment.getExternalStorageDirectory().getFreeSpace() / 1024 / 1024;
    }
}
