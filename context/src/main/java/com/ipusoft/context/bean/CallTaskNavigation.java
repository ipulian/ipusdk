package com.ipusoft.context.bean;

import com.chad.library.adapter.base.entity.JSectionEntity;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 4/6/21 6:35 PM
 */

public class CallTaskNavigation extends JSectionEntity implements Serializable {
    private static final long serialVersionUID = 200791783395541596L;
    private final boolean isHeader;
    private final CallTask callTask;


    public CallTaskNavigation(boolean isHeader, CallTask callTask) {
        this.isHeader = isHeader;
        this.callTask = callTask;
    }

    public CallTask getCallTask() {
        return callTask;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }
}
