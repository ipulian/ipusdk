package com.ipusoft.context.iface;


import com.ipusoft.context.bean.SipResponse;

/**
 * author : GWFan
 * time   : 1/11/21 6:38 PM
 * desc   : sip状态变化的listener
 */

public interface BaseSipStatusChangedListener {

    /**
     * 初始化状态发生改变
     */
    void onInitCodeChanged(int code, String msg);

    /**
     * 登陆状态发生改变
     */
    void onLoginCodeChanged(SipResponse sipResponse);

    /**
     * http状态发生改变
     */
    void onHttpCodeChanged(SipResponse sipResponse);

    /**
     * 通话状态发生改变
     */
    void onCallStatusCodeChanged(SipResponse sipResponse);
}
