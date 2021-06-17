package com.ipusoft.context.bean;


import com.ipusoft.context.bean.base.BaseHttpResponse;

/**
 * author : GWFan
 * time   : 1/11/21 5:41 PM
 * desc   : 坐席信息
 */

public class SeatInfo extends BaseHttpResponse {
    private static final long serialVersionUID = -20455796439021343L;
    private String password;
    private String apiKey;
    private String sdkSecret;
    private String seatNo;
    private String callType;

    private SeatInfo() {
    }

    public SeatInfo(String seatNo, String apiKey, String sdkSecret, String password) {
        this.password = password;
        this.apiKey = apiKey;
        this.sdkSecret = sdkSecret;
        this.seatNo = seatNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSdkSecret() {
        return sdkSecret;
    }

    public void setSdkSecret(String sdkSecret) {
        this.sdkSecret = sdkSecret;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }
}
