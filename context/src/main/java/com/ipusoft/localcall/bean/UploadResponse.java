package com.ipusoft.localcall.bean;

import com.ipusoft.context.bean.base.HttpResponse;

/**
 * author : GWFan
 * time   : 5/18/21 5:49 PM
 * desc   :
 */

public class UploadResponse extends HttpResponse {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
