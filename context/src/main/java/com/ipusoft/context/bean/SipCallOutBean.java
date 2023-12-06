package com.ipusoft.context.bean;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 6/1/21 4:58 PM
 * desc   :
 */

public class SipCallOutBean implements Serializable {
    //被叫号码
    private String phone;
    //隐号后的号码
    private String hPhone;
    //被叫归属地
    private String phoneArea;
    //通话状态
    private String sipStatus;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String number) {
        this.phone = number;
    }

    public String getHPhone() {
        return hPhone;
    }

    public void setHPhone(String hNumber) {
        this.hPhone = hNumber;
    }

    public String getPhoneArea() {
        return phoneArea;
    }

    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea;
    }

    public String getSipStatus() {
        return sipStatus;
    }

    public void setSipStatus(String sipStatus) {
        this.sipStatus = sipStatus;
    }
}
