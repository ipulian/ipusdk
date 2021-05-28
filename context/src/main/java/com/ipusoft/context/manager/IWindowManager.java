package com.ipusoft.context.manager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;

import com.ipusoft.context.utils.SizeUtils;

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
     * 自定义宽高的悬浮窗
     *
     * @param width
     * @param height
     * @return
     */
    public static WindowManager.LayoutParams getWindowParams(int width, int height) {
        WindowManager.LayoutParams layoutParams = initWindowParams();
        layoutParams.y = 0;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.gravity = Gravity.TOP;
        if (width <= 0) {
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        } else {
            layoutParams.width = SizeUtils.dp2px(height);
        }
        if (height <= 0) {
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        } else {
            layoutParams.height = SizeUtils.dp2px(height);
        }
        return layoutParams;
    }

    /**
     * 自定义高度的悬浮窗
     *
     * @param height
     * @return
     */
    public static WindowManager.LayoutParams getWindowParams(int height) {
        WindowManager.LayoutParams layoutParams = initWindowParams();
        layoutParams.y = 0;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.gravity = Gravity.TOP;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        if (height <= 0) {
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        } else {
            layoutParams.height = SizeUtils.dp2px(height);
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
}
