package com.ipusoft.context.bean;

import com.ipusoft.utils.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * author : GWFan
 * time   : 5/18/21 11:43 AM
 * desc   :
 */

public class AuthInfo implements Serializable {

    private String key;

    private String secret;

    private String username;

    private String password;

    private AuthInfo() {
    }

    public AuthInfo(String key, String secret, String username) {
        this.key = key;
        this.secret = secret;
        this.username = username;
    }

    public AuthInfo(String key, String secret, String username, String password) {
        this.key = key;
        this.secret = secret;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return Objects.hash(username, key, secret);
    }
}
