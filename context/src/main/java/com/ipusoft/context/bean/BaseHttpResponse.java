package com.ipusoft.context.bean;

import com.ipusoft.context.constant.HttpStatus;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 4/22/21 6:03 PM
 * desc   : 接口返回数据的基类
 */

public class BaseHttpResponse implements Serializable {

    private static final long serialVersionUID = -6342197338772826935L;
    /**
     * 状态码
     */
    private String status = HttpStatus.SUCCESS;
    /**
     * 状态消息
     */
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}