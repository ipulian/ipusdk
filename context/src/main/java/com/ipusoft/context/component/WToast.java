package com.ipusoft.context.component;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.R;
import com.ipusoft.context.manager.IWindowManager;
import com.ipusoft.context.utils.ThreadUtils;

/**
 * author : GWFan
 * time   : 5/8/21 9:18 AM
 * desc   :
 */

public class WToast {
    private static WindowManager windowManager;
    private static View windowView;

    public static synchronized void showLoading() {
        showLoading("正在加载");
    }

    public static synchronized void showLoading(String msg) {
        dismiss();
        windowManager = IWindowManager.getWindowManager(AppContext.getAppContext());
        windowView = View.inflate(AppContext.getAppContext(), R.layout.context_layout_custom_loading, null);
        try {
            WindowManager.LayoutParams mParams = IWindowManager.getWindowToastParams();
            TextView textView = windowView.findViewById(R.id.tv_msg);
            LoadingView loadingView = windowView.findViewById(R.id.loading);
            loadingView.setSize(80);
            textView.setText(msg);
            windowManager.addView(windowView, mParams);
            ThreadUtils.runOnUiThreadDelayed(WToast::dismiss, 8 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showMessage(String msg) {
        dismiss();
        windowManager = IWindowManager.getWindowManager(AppContext.getAppContext());
        windowView = View.inflate(AppContext.getAppContext(), R.layout.context_window_layout_custom_toast, null);
        try {
            WindowManager.LayoutParams mParams = IWindowManager.getWindowToastParams();
            TextView textView = windowView.findViewById(R.id.tv_msg);
            textView.setText(msg);
            windowManager.addView(windowView, mParams);
            ThreadUtils.runOnUiThreadDelayed(WToast::dismiss, 1100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏自定义Dialog
     */
    public static void dismiss() {
        try {
            if (windowView != null) {
                if (windowView.getParent() != null) {
                    windowManager.removeView(windowView);
                }
                windowManager = null;
                windowView = null;
            }
        } catch (Exception e) {
            windowManager = null;
            windowView = null;
            e.printStackTrace();
        }
    }
}
