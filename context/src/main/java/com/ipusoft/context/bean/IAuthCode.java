package com.ipusoft.context.bean;


import com.ipusoft.context.bean.base.HttpResponse;

/**
 * author : GWFan
 * time   : 5/17/21 3:42 PM
 * desc   :
 */

public class IAuthCode extends HttpResponse {
    private String authCode;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
