package com.ipusoft.context.listener;

/**
 * author : GWFan
 * time   : 1/14/21 5:33 PM
 * desc   : 通话状态改变的回调
 */

public interface OnPhoneStateChangedListener {

    //响铃
    void onDialingListener();

    //呼入
    void onInComingListener();

    //接通
    void onConnectedListener();

    //挂断
    void onDisConnectedListener();
}