package com.ipusoft.context.bridge;

/**
 * author : GWFan
 * time   : 5/13/21 11:01 AM
 * desc   :
 */

public interface NativeJSBridge {

    /**
     * Since 1.6.9 Deprecated
     *
     * @param phone 电话号码
     */
    @Deprecated()
    void call(String phone);

    /**
     * Since 1.6.19, 老版本中，SDK只能接收phone,并且使用X外呼，
     * 从1.6.19开始 SDK使用的外呼方式由H5端决定,可以是X或者SIM,暂不支持SIP
     *
     * @param type
     * @param phone
     */
    void call(String type, String phone);

    void goBack();
}
