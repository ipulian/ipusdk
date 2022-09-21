package com.ipusoft.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.ipusoft.context.R;


/**
 * author : GWFan
 * time   : 2020/5/26 10:28
 * desc   :
 */

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtils(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (mTextView != null) {
            String str = "重新发送(" + millisUntilFinished / 1000 + "s)";
            mTextView.setText(str);
            mTextView.setTextColor(ResourceUtils.getColor(R.color.textColor8));
            mTextView.setEnabled(false);
        }
    }

    @Override
    public void onFinish() {
        if (mTextView != null) {
            mTextView.setText(R.string.re_request);
            mTextView.setTextColor(ResourceUtils.getColor(R.color.themeColor));
            mTextView.setEnabled(true);
        }
    }
}
