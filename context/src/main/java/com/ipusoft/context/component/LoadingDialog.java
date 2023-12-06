package com.ipusoft.context.component;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ipusoft.context.R;
import com.ipusoft.context.component.base.BaseDialogFragment;
import com.ipusoft.context.databinding.ContextLayoutCustomLoadingBinding;
import com.ipusoft.context.viewmodel.BaseViewModel;
import com.elvishew.xlog.XLog;
import com.ipusoft.utils.ExceptionUtils;

/**
 * author : GWFan
 * time   : 6/11/21 9:16 AM
 * desc   :
 */

public class LoadingDialog extends BaseDialogFragment<ContextLayoutCustomLoadingBinding, BaseViewModel> {
    private static final String TAG = "LoadingDialog";

    private String msg = "正在加载";

    private boolean statusBarDarkFont = false;

    private ICountDownTimer iCountDownTimer;

    public static final String MSG = "msg";
    public static final String STATUS_BAR_DARK_FONT = "statusBarDarkFont";

    public static LoadingDialog getInstance() {
        return new LoadingDialog();
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            msg = bundle.getString(MSG, "正在加载");
            statusBarDarkFont = bundle.getBoolean(STATUS_BAR_DARK_FONT, false);
        }
    }

    @Override
    protected void initUI() {
        super.initUI();
        binding.tvMsg.setText(msg);
    }

    @Override
    public void show() {
        if (isAdded() || isVisible() || isShowing) {
            return;
        }
        super.show();
        try {
            if (iCountDownTimer != null) {
                iCountDownTimer.onFinish();
            }
            iCountDownTimer = new ICountDownTimer(9 * 1000, 1000);
            iCountDownTimer.start();
        } catch (Exception exception) {
            exception.printStackTrace();
            XLog.e(TAG + "->show->" + ExceptionUtils.getErrorInfo(exception));
        }
    }

    @Override
    protected boolean statusBarDarkFont() {
        return statusBarDarkFont;
    }

//    @Override
//    protected boolean hideNavigationBar() {
//        return true;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.LoadingDialog);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        try {
            if (iCountDownTimer != null) {
                iCountDownTimer.onFinish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            XLog.e(TAG + "->dismiss：" + ExceptionUtils.getErrorInfo(e));
        }
    }

    @Override
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
        try {
            if (iCountDownTimer != null) {
                iCountDownTimer.onFinish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            XLog.e(TAG + "->dismiss：" + ExceptionUtils.getErrorInfo(e));
        }
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
                if (isAdded() || isVisible()) {
                    if (isShowing) {
                        isShowing = false;
                        if (iCountDownTimer != null) {
                            iCountDownTimer.cancel();
                            iCountDownTimer = null;
                        }
                        dismissAllowingStateLoss();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                XLog.e(TAG + "->onFinish：" + ExceptionUtils.getErrorInfo(e));
            }
        }
    }
}
