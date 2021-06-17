package com.ipusoft.context.utils;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.ipusoft.context.IpuSoftSDK;

/**
 * author : GWFan
 * time   : 5/2/21 9:41 PM
 * desc   :
 */

public class ColorUtils {

    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(IpuSoftSDK.getAppContext(), id);
    }
}
