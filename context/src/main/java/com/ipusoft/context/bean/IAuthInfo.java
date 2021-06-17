package com.ipusoft.context.bean;

import com.ipusoft.context.utils.StringUtils;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 6/4/21 6:14 PM
 * desc   :
 */

public class IAuthInfo implements Serializable {

    private String token = "";

    private String uid = "";

    public IAuthInfo() {
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        IAuthInfo other = (IAuthInfo) obj;
        return StringUtils.equals(uid, other.uid);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((StringUtils.isEmpty(uid)) ? 0 : uid.hashCode());
        return result;
    }
}
