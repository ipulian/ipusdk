package com.ipusoft.context.component;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.R;
import com.ipusoft.context.utils.ThreadUtils;

/**
 * author : GWFan
 * time   : 6/11/21 9:16 AM
 * desc   :
 */

public class LoadingDialog extends DialogFragment {
    private static final String TAG = "LoadingDialog";

    private String msg;
    private ICountDownTimer iCountDownTimer;
    private boolean isShowing = false;

    private LoadingDialog() {
    }

    private static class LoadingDialogHolder {
        private static final LoadingDialog INSTANCE = new LoadingDialog();
    }

    public static LoadingDialog get() {
        Bundle args = new Bundle();
        LoadingDialog fragment = new LoadingDialog();
        fragment.setArguments(args);
        return LoadingDialogHolder.INSTANCE;
    }

    public LoadingDialog setText(String msg) {
        this.msg = msg;
        return this;
    }

    public synchronized void show() {
        show(getClass().getSimpleName());
    }

    public synchronized void show(String tag) {
        FragmentActivity mActivity = AppContext.getActivityContext();
        if (mActivity != null) {
            ThreadUtils.runOnUiThread(() -> {
                FragmentManager fm = mActivity.getSupportFragmentManager();
                Fragment prev = fm.findFragmentByTag(tag);
                if (prev != null) {
                    fm.beginTransaction().remove(prev);
                }
                if (isShowing) {
                    dismiss();
                }
                show(fm, tag);
                isShowing = true;
                iCountDownTimer = new ICountDownTimer(10 * 1000, 1000);
                iCountDownTimer.start();
            });
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isShowing = false;
        try {
            if (iCountDownTimer != null) {
                iCountDownTimer.onFinish();
                iCountDownTimer.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog mDialog = getDialog();
        if (mDialog != null) {
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            window.setAttributes(params);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.LoadingDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setCancelable(false);
        View view = inflater.inflate(R.layout.context_layout_custom_loading, container, false);
        TextView textView = view.findViewById(R.id.tv_msg);
        textView.setText(msg);
        return view;
    }

    class ICountDownTimer extends CountDownTimer {

        public ICountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            try {
                if (LoadingDialog.this.isVisible()) {
                    LoadingDialog.this.dismiss();
                    this.cancel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
