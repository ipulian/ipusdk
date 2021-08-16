package com.ipusoft.context.bean.base;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 6/5/21 9:22 AM
 * desc   :
 */

public class FloatingWindowStatus implements Serializable {

    private boolean isShow;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
