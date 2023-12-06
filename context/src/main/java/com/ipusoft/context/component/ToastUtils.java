package com.ipusoft.context.component;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.R;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.StringUtils;

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
        Log.d(TAG, "ToastUtils: -------------showLoading1");
        showLoading("正在加载");
    }

    public static synchronized void showLoading(String msg) {
        Log.d(TAG, "ToastUtils: -------------showLoading2");
        if (StringUtils.isNotEmpty(msg)) {
            try {
                AppCompatActivity activityContext = AppContext.getActivityContext();
                if (activityContext != null) {
                    LoadingDialog dialog = LoadingDialog.getInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString(LoadingDialog.MSG, msg);
                    dialog.setArguments(bundle);
                    dialog.show();
                    dialogWeakReference = new WeakReference<>(dialog);
                }
            } catch (Exception e) {
                e.printStackTrace();
                XLog.e(TAG + "->showLoading->" + ExceptionUtils.getErrorInfo(e));
            }
        }
    }

    public static synchronized void showLoading(boolean statusBarDarkFont) {
        Log.d(TAG, "ToastUtils: -------------showLoading3");
        showLoading("正在加载", statusBarDarkFont);
    }

    public static synchronized void showLoading(String msg, boolean statusBarDarkFont) {
        Log.d(TAG, "ToastUtils: -------------showLoading4");
        if (StringUtils.isNotEmpty(msg)) {
            try {
                AppCompatActivity activityContext = AppContext.getActivityContext();
                if (activityContext != null) {
                    LoadingDialog dialog = LoadingDialog.getInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString(LoadingDialog.MSG, msg);
                    bundle.putBoolean(LoadingDialog.STATUS_BAR_DARK_FONT, statusBarDarkFont);
                    dialog.setArguments(bundle);
                    dialog.show();
                    dialogWeakReference = new WeakReference<>(dialog);
                }
            } catch (Exception e) {
                e.printStackTrace();
                XLog.e(TAG + "->showLoading->" + ExceptionUtils.getErrorInfo(e));
            }
        }
    }

    /**
     * 消息提示
     *
     * @param msg 提示内容
     */
    public static void showMessage(String msg) {
        if (StringUtils.isNotEmpty(msg)) {
            try {
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
            } catch (Exception e) {
                e.printStackTrace();
                XLog.e(TAG + "->showMessage->" + ExceptionUtils.getErrorInfo(e));
            }
        }
    }

    public static synchronized void dismiss() {
        Log.d(TAG, "ToastUtils: -------------dismiss");
        try {
            if (dialogWeakReference != null) {
                LoadingDialog dialog = dialogWeakReference.get();
                if (dialog != null) {
                    dialog.dismissAllowingStateLoss();
                    dialogWeakReference = null;
//                    if (dialog.isAdded() || dialog.isVisible()) {
//                        dialog.dismissAllowingStateLoss();
//                        dialogWeakReference = null;
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
