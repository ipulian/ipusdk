package com.ipusoft.context.bean;

import com.ipusoft.utils.StringUtils;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        AuthInfo other = (AuthInfo) obj;
        return StringUtils.equals(key, other.key)
                && StringUtils.equals(secret, other.secret)
                && StringUtils.equals(username, other.username);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((StringUtils.isEmpty(username)) ? 0 : username.hashCode());
        result = result * 31 + key.hashCode();
        result = result * 31 + secret.hashCode();
        return result;
    }
}
