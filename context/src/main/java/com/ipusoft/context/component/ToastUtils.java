package com.ipusoft.context.component;

import android.app.Application;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.R;

import java.lang.ref.WeakReference;


/**
 * author : GWFan
 * time   : 2020/4/30 09:12
 * desc   :
 */

public class ToastUtils {
    private static final String TAG = "ToastUtils";
    private static WeakReference<LoadingDialog> dialogWeakReference;
    private static WeakReference<Toast> toastWeakReference;

    /**
     * 显示LoadingDialog
     */
    public static synchronized void showLoading() {
        showLoading("正在加载");
    }

    public static synchronized void showLoading(String msg) {
        dismiss();
        AppCompatActivity activityContext = AppContext.getActivityContext();
        if (activityContext != null) {
            LoadingDialog dialog = LoadingDialog.get().setText(msg);
            dialog.show();
            dialogWeakReference = new WeakReference<>(dialog);
        }
    }

    /**
     * 消息提示
     *
     * @param msg
     */
    public static void showMessage(String msg) {
        dismiss();
        Toast toast;
        if (toastWeakReference != null) {
            toast = toastWeakReference.get();
            if (toast != null) {
                toast.cancel();
            }
        }
        Application appContext = AppContext.getAppContext();
        toast = new Toast(appContext);
        View view = LayoutInflater.from(appContext).inflate(R.layout.context_layout_custom_toast, null, false);
        TextView textView = view.findViewById(R.id.tv_msg);
        textView.setText(msg);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        toastWeakReference = new WeakReference<>(toast);
    }

    public static synchronized void dismiss() {
        try {
            if (dialogWeakReference != null) {
                LoadingDialog dialog = dialogWeakReference.get();
                if (dialog != null) {
                    if (!dialog.isHidden()) {
                        dialog.dismiss();
                        dialogWeakReference = null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
