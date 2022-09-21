package com.ipusoft.context.iface;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * author : GWFan
 * time   : 6/25/21 5:17 PM
 * desc   : ViewModel接口管理类,定义了一些ViewModel中的常用操作
 */

public interface ViewModelManager {
    /**
     * 是否显示Loading视图
     */
    void showLoading();

    void showLoading(String msg);

    /**
     * 弹出Toast提示
     */
    void showMessage(String msg);

    /**
     * 页面跳转
     */
    void navigate(Intent intent);

    void navigate(Class<? extends AppCompatActivity> clazz);

    /**
     * 身份过期
     */
    void sessionExpired();

    /**
     * 退出当前页面
     */
    void finish();

    void finish(long delayed);

    /**
     * 隐藏Dialog
     */
    void dismiss();
}
