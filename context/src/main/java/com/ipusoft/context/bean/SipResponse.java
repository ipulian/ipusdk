package com.ipusoft.context.bean;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 1/11/21 6:42 PM
 * desc   : sip 状态
 */

public class SipResponse implements Serializable {
    private String date;

    private String type;
    private int code;
    //错误消息
    private String msg;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
