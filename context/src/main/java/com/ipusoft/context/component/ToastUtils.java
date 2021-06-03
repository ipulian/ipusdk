package com.ipusoft.context.component;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.IpuSoftSDK;
import com.ipusoft.context.R;


/**
 * author : GWFan
 * time   : 2020/4/30 09:12
 * desc   :
 */

public class ToastUtils {
    private static final String TAG = "ToastUtils";
    private static final Handler handler = new Handler();
    private static Toast toastLoading;

    /**
     * 显示LoadingDialog
     */
    public static synchronized void showLoading() {
        showLoading("正在加载");
    }

    public static synchronized void showLoading(String msg) {
        View view = View.inflate(AppContext.getAppContext(), R.layout.context_layout_custom_loading, null);
        TextView textView = view.findViewById(R.id.tv_msg);
        LoadingView loadingView = view.findViewById(R.id.loading);
        loadingView.setSize(80);
        textView.setText(msg);
        if (toastLoading != null) {
            toastLoading.cancel();
        }
        toastLoading = new Toast(AppContext.getAppContext());
        toastLoading.setGravity(Gravity.CENTER, 0, 0);
        toastLoading.setDuration(Toast.LENGTH_LONG);
        toastLoading.setView(view);
        toastLoading.show();
        handler.postDelayed(toastLoading::cancel, 9 * 1000);
    }

    /**
     * 消息提示
     *
     * @param msg
     */
    public static void showMessage(String msg) {
        dismiss();
        Toast toast = new Toast(AppContext.getAppContext());
        View view = LayoutInflater.from(IpuSoftSDK.getAppContext()).inflate(R.layout.context_layout_custom_toast, null, false);
        TextView textView = view.findViewById(R.id.tv_msg);
        textView.setText(msg);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void dismiss() {
        try {
            if (toastLoading != null) {
                toastLoading.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
