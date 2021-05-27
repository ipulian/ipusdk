package com.ipusoft.context.bean;


import com.ipusoft.context.bean.base.BaseHttpResponse;

/**
 * author : GWFan
 * time   : 5/17/21 3:53 PM
 * desc   :
 */

public class IToken extends BaseHttpResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
