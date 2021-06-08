package com.ipusoft.context.bean;

import com.ipusoft.context.bean.base.BaseHttpResponse;

/**
 * author : GWFan
 * time   : 5/14/21 10:00 AM
 * desc   :
 */

public abstract class SimRiskControlBean extends BaseHttpResponse {
    /*
     * 0直接外呼  1无法外呼提示msg  2提示msg，提供继续外呼和取消外呼
     */
    private int type;
    /*
     * 外呼号码
     */
    private String phone;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
