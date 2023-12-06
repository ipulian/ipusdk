package com.ipusoft.localcall.bean;

import java.io.Serializable;

/**
 * @author : GWFan
 * time   : 2023/7/26 18:47
 * desc   :
 */

public class WebCallTask implements Serializable {
    private String phone;
    private long callTime;
    private String taskId = "0";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getCallTime() {
        return callTime;
    }

    public void setCallTime(long callTime) {
        this.callTime = callTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
