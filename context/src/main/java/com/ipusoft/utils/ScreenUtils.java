package com.ipusoft.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import com.ipusoft.context.AppContext;


/**
 * author : GWFan
 * time   : 5/2/21 11:55 PM
 * desc   :
 */
public class ScreenUtils {

    /**
     * @return the application's width of screen, in pixel
     */
    public static int getAppScreenWidth() {
        WindowManager wm = (WindowManager) AppContext.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    /**
     * @return the application's height of screen, in pixel
     */
    public static int getAppScreenHeight() {
        WindowManager wm = (WindowManager) AppContext.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }
}
