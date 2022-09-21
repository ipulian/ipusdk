package com.ipusoft.context.bean.base;

import com.google.gson.annotations.SerializedName;

/**
 * author : GWFan
 * time   : 10/28/21 10:45 AM
 * desc   :
 */

public class IResponse<T> extends HttpResponse {

    @SerializedName(value = "rows", alternate = {"data", "result", "customerId"})
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
