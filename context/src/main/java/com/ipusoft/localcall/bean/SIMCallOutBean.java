package com.ipusoft.localcall.bean;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 4/29/21 4:42 PM
 * desc   :
 */

public class SIMCallOutBean implements Serializable {
    private String phone;
    private Long timestamp;

    private String callTime;

    public SIMCallOutBean(String phone, String callTime) {
        this.phone = phone;
        this.callTime = callTime;
    }

    public SIMCallOutBean(String phone, String callTime, Long timestamp) {
        this.phone = phone;
        this.callTime = callTime;
        this.timestamp = timestamp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }
}
