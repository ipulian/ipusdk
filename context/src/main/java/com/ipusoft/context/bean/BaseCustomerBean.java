package com.ipusoft.context.bean;

import com.ipusoft.context.bean.base.BaseCustomerClueBean;
import com.ipusoft.context.constant.HttpStatus;

/**
 * author : GWFan
 * time   : 7/29/21 2:49 PM
 * desc   :
 */

public class BaseCustomerBean extends BaseCustomerClueBean {

    private static final long serialVersionUID = -6342197338772826935L;
    /**
     * 状态码
     */
    private String status = HttpStatus.SUCCESS;
    /**
     * 状态消息
     */
    private String msg;

    public String getHttpStatus() {
        return status;
    }

    public void setHttpStatus(String httpStatus) {
        this.status = httpStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
