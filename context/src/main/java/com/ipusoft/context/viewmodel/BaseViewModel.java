package com.ipusoft.context.viewmodel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ipusoft.context.iface.ViewModelManager;


/**
 * author : GWFan
 * time   : 6/25/21 6:17 PM
 * desc   : ViewModel 的基类，主要封装一些ViewModel中的通用能力，包括展示Loading,Toast,finish当前页面,
 * 跳转指定页面,身份过期,接收Intent参数等。
 */

public class BaseViewModel extends ViewModel implements ViewModelManager {
    private static final String TAG = "BaseViewModel";
    /**
     * 显示Loading
     */
    public MutableLiveData<Object> showLoadingLiveData;

    public MutableLiveData<String> showMsgLoadingLiveData;

    /**
     * 显示Toast
     */
    public MutableLiveData<String> showToastLiveData;

    /**
     * 页面跳转
     */
    public MutableLiveData<Intent> navigateLiveData;

    public MutableLiveData<Class<? extends AppCompatActivity>> navigateLiveData2;

    /**
     * 结束当前页面
     */
    public MutableLiveData<Object> finishLiveData;

    public MutableLiveData<Long> finishDelayedLiveData;

    //隐藏DialogFragment
    public MutableLiveData<Object> dismissLiveData;

    /**
     * 身份过期
     */
    public MutableLiveData<Object> sessionExpiredLiveData;

    @Keep
    public BaseViewModel() {
        showLoadingLiveData = new MutableLiveData<>();

        showMsgLoadingLiveData = new MutableLiveData<>();

        showToastLiveData = new MutableLiveData<>();

        navigateLiveData = new MutableLiveData<>();

        navigateLiveData2 = new MutableLiveData<>();

        sessionExpiredLiveData = new MutableLiveData<>();

        finishLiveData = new MutableLiveData<>();

        finishDelayedLiveData = new MutableLiveData<>();

        dismissLiveData = new MutableLiveData<>();
    }

    /**
     * Activity中使用
     *
     * @param intent
     */
    @Keep
    public BaseViewModel(Intent intent) {
        this();
        if (intent != null) {
            getParamsByIntent(intent);
        }
    }

    /**
     * Fragment中使用
     *
     * @param bundle
     */
    @Keep
    public BaseViewModel(Bundle bundle) {
        this();
        if (bundle != null) {
            getParamsByIntent(bundle);
        }
    }

    /**
     * Activity中使用
     *
     * @param intent
     */
    protected void getParamsByIntent(Intent intent) {

    }

    /**
     * Fragment中使用
     *
     * @param bundle
     */
    protected void getParamsByIntent(Bundle bundle) {

    }

    public void initUI() {

    }

    @Override
    public void showLoading() {
        showLoadingLiveData.postValue(new Object());
    }

    @Override
    public void showLoading(String msg) {
        showMsgLoadingLiveData.postValue(msg);
    }

    @Override
    public void showMessage(String msg) {
        showToastLiveData.postValue(msg);
    }

    @Override
    public void navigate(Intent intent) {
        navigateLiveData.postValue(intent);
    }

    @Override
    public void navigate(Class<? extends AppCompatActivity> clazz) {
        navigateLiveData2.postValue(clazz);
    }

    @Override
    public void sessionExpired() {
        sessionExpiredLiveData.postValue(new Object());
    }

    @Override
    public void finish() {
        finishLiveData.postValue(new Object());
    }

    @Override
    public void finish(long delayed) {
        finishDelayedLiveData.postValue(delayed);
    }

    @Override
    public void dismiss() {
        dismissLiveData.postValue(new Object());
    }
}
