package com.ipusoft.context.bean;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 5/18/21 11:43 AM
 * desc   :
 */

public class AuthInfo implements Serializable {

    private String key;

    private String secret;

    private String username;

    private AuthInfo() {
    }

    public AuthInfo(String key, String secret, String username) {
        this.key = key;
        this.secret = secret;
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
