package com.ipusoft.localcall.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * author : GWFan
 * time   : 4/29/21 4:42 PM
 * desc   :
 */

public class SIMCallOutBean implements Serializable {
    private String phone;
    private Long timestamp;

    private String releaseTime;//挂断时间
    private String callTime;//外呼时间

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

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        SIMCallOutBean other = (SIMCallOutBean) obj;
        if (timestamp == null || timestamp == 0) {
            return other.getTimestamp() == null || other.getTimestamp() == 0;
        } else return timestamp.equals(other.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(timestamp);
    }
}
