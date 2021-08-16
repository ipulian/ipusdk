package com.ipusoft.context.constant;

/**
 * author : GWFan
 * time   : 6/2/21 3:57 PM
 * desc   :
 */

public enum SipState {

    STATUS_0(0, "无通话"),
    STATUS_1(1, "正在外呼"),
    STATUS_2(2, "正在呼入"),
    STATUS_3(3, "已振铃"),
    STATUS_4(4, "已响应"),
    STATUS_5(5, "已接通"),
    STATUS_6(6, "已挂断");

    private final int status;
    private final String str;

    SipState(int status, String str) {
        this.status = status;
        this.str = str;
    }

    public int getStatus() {
        return status;
    }

    public String getStr() {
        return str;
    }
}
