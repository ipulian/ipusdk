package com.ipusoft.context.manager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;

import com.ipusoft.context.AppContext;

/**
 * author : GWFan
 * time   : 5/28/21 10:36 AM
 * desc   :
 */

public class IWindowManager {

    private static WindowManager.LayoutParams layoutParams;

    public static WindowManager getWindowManager() {
        return (WindowManager) AppContext.getAppContext().getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * 悬浮窗
     *
     * @return
     */
    public static WindowManager.LayoutParams getWindowParams() {
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
                    layoutParams.y = 0;
                    layoutParams.format = PixelFormat.RGBA_8888;
                    layoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                    layoutParams.gravity = Gravity.TOP;
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                }
            }
        }
        return layoutParams;
    }
}
