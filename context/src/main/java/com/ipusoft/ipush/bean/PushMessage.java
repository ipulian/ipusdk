package com.ipusoft.ipush.bean;

import com.ipusoft.context.bean.base.HttpResponse;

/**
 * author : GWFan
 * time   : 2021/12/11 16:37
 * desc   :
 */

public class PushMessage extends HttpResponse {
    public String callMessage;

    public String getCallMessage() {
        return callMessage;
    }

    public void setCallMessage(String callMessage) {
        this.callMessage = callMessage;
    }
}
