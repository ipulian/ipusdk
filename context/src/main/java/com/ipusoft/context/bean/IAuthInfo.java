package com.ipusoft.context.bean;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 6/4/21 6:14 PM
 * desc   :
 */

public class IAuthInfo implements Serializable {

    private String token;

    private String uid;

    public IAuthInfo(){}

    public IAuthInfo(String token, String uid) {
        this.token = token;
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
