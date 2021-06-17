package com.ipusoft.context.manager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * author : GWFan
 * time   : 5/28/21 10:36 AM
 * desc   :
 */

public class IWindowManager {

    private static WindowManager.LayoutParams layoutParams;

    public static WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public static WindowManager.LayoutParams initWindowParams() {
        if (layoutParams == null) {
            synchronized (IWindowManager.class) {
                if (layoutParams == null) {
                    layoutParams = new WindowManager.LayoutParams();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                    } else {
                        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                                | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
                    }
                }
            }
        }
        return layoutParams;
    }

    /**
     * 全屏的悬浮窗
     *
     * @return
     */
    public static WindowManager.LayoutParams getWindowParams() {
        WindowManager.LayoutParams layoutParams = initWindowParams();
        layoutParams.y = 0;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.gravity = Gravity.TOP;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        return layoutParams;
    }


    public static WindowManager.LayoutParams getWindowToastParams() {
        WindowManager.LayoutParams layoutParams = initWindowParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        return layoutParams;
    }

    public static WindowManager.LayoutParams getWrapLayoutParams() {
        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                    | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        }
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        mLayoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        return mLayoutParams;
    }
}
