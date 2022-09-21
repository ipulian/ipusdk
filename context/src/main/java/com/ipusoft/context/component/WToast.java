package com.ipusoft.context.component;

import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.R;
import com.ipusoft.context.manager.IWindowManager;
import com.ipusoft.logger.XLogger;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.ThreadUtils;

import java.lang.ref.WeakReference;

/**
 * author : GWFan
 * time   : 5/8/21 9:18 AM
 * desc   :
 */

public class WToast {
    private static final String TAG = "WToast";

    private static WindowManager windowManager;
    private static WeakReference<View> windowViewReference;
    private static WindowManager.LayoutParams layoutParams;

    public static synchronized void showLoading() {
        showLoading("正在加载");
    }

    public static synchronized void showLoading(String msg) {
        try {
            dismiss();
            windowManager = IWindowManager.getWindowManager();
            View view = View.inflate(AppContext.getAppContext(), R.layout.context_layout_custom_loading, null);
            WindowManager.LayoutParams mParams = getWindowToastParams();
            if (mParams != null) {
                TextView textView = view.findViewById(R.id.tv_msg);
                LoadingView loadingView = view.findViewById(R.id.loading);
                loadingView.setSize(80);
                textView.setText(msg);
                windowManager.addView(view, mParams);
                windowViewReference = new WeakReference<>(view);
                ThreadUtils.runOnUiThreadDelayed(WToast::dismiss, 8 * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            XLogger.e(TAG + "->showLoading->" + ExceptionUtils.getErrorInfo(e));
        }
    }

    public static void showMessage(String msg) {
        try {
            dismiss();
            windowManager = IWindowManager.getWindowManager();
            View view = View.inflate(AppContext.getAppContext(), R.layout.context_window_layout_custom_toast, null);
            WindowManager.LayoutParams mParams = getWindowToastParams();
            if (mParams != null) {
                TextView textView = view.findViewById(R.id.tv_msg);
                textView.setText(msg);
                windowManager.addView(view, mParams);
                windowViewReference = new WeakReference<>(view);
                ThreadUtils.runOnUiThreadDelayed(WToast::dismiss, 1100);
            }
        } catch (Exception e) {
            e.printStackTrace();
            XLogger.e(TAG + "->showMessage->" + ExceptionUtils.getErrorInfo(e));
        }
    }

    /**
     * 隐藏自定义Dialog
     */
    public static void dismiss() {
        try {
            if (windowViewReference != null) {
                View view = windowViewReference.get();
                if (view != null) {
                    if (view.getParent() != null) {
                        windowManager.removeViewImmediate(view);
                    }
                    windowManager = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            windowManager = null;
        }
    }


    public static WindowManager.LayoutParams getWindowToastParams() {
        try {
            if (layoutParams == null) {
                synchronized (WToast.class) {
                    if (layoutParams == null) {
                        layoutParams = new WindowManager.LayoutParams();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                        } else {
                            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                                    | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
                        }
                        layoutParams.gravity = Gravity.CENTER;
                        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        layoutParams.format = PixelFormat.TRANSLUCENT;
                        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            XLogger.e(TAG + "->getWindowToastParams->" + ExceptionUtils.getErrorInfo(e));
        }
        return layoutParams;
    }
}
