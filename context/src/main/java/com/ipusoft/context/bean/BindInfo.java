package com.ipusoft.context.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * author : GWFan
 * time   : 7/30/20 12:21 PM
 * desc   : 绑定信息
 */

public class BindInfo implements Serializable {

    private static final long serialVersionUID = 1390158629070230236L;
    private String virtualNumber;
    @SerializedName("xphone")
    private String xPhone;
    private String phoneArea;
    @SerializedName("xphoneArea")
    private String xPhoneArea;
    private List<String> caller;

    private String channelName;

    public String getVirtualNumber() {
        return virtualNumber;
    }

    public void setVirtualNumber(String virtualNumber) {
        this.virtualNumber = virtualNumber;
    }

    public String getXPhone() {
        return xPhone;
    }

    public void setXPhone(String xPhone) {
        this.xPhone = xPhone;
    }

    public List<String> getCaller() {
        return caller;
    }

    public void setCaller(List<String> caller) {
        this.caller = caller;
    }

    public String getPhoneArea() {
        return phoneArea;
    }

    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea;
    }

    public String getXPhoneArea() {
        return xPhoneArea;
    }

    public void setXPhoneArea(String xPhoneArea) {
        this.xPhoneArea = xPhoneArea;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
