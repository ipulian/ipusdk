package com.ipusoft.context.iface;


import com.ipusoft.context.bean.SipResponse;

/**
 * author : GWFan
 * time   : 1/11/21 6:38 PM
 * desc   : sip状态变化的listener
 */

public interface BaseSipStatusChangedListener {
    /**
     * SIP发生错误
     */
    void onSipResponseError(SipResponse sipResponse);

    /**
     * SIP状态正常
     */
    void onSipResponseSuccess(SipResponse sipResponse);
}
