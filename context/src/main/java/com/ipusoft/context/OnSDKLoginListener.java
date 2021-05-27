package com.ipusoft.context;

/**
 * author : GWFan
 * time   : 5/27/21 10:41 AM
 * desc   :
 */

public interface OnSDKLoginListener {
    enum LoginStatus {
        SUCCESS,
        FAILED
    }

    void onLoginResult(LoginStatus status);
}
