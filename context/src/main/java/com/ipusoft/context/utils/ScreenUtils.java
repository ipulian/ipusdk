package com.ipusoft.context.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import com.ipusoft.context.IpuSoftSDK;


/**
 * author : GWFan
 * time   : 5/2/21 11:55 PM
 * desc   :
 */
public class ScreenUtils {

    public static int getAppScreenWidth() {
        WindowManager wm = (WindowManager) IpuSoftSDK.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    /**
     * @return the application's height of screen, in pixel
     */
    public static int getAppScreenHeight() {
        WindowManager wm = (WindowManager) IpuSoftSDK.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }
}
