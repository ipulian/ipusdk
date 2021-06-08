package com.ipusoft.context.bridge;

/**
 * author : GWFan
 * time   : 5/13/21 11:01 AM
 * desc   :
 */

public interface NativeJSBridge {

    /**
     * @param phone 电话号码
     */
    @Deprecated
    void call(String phone);

    void call(String type, String phone);

    void goBack();
}
