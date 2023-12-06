package com.ipusoft.context.bean;

import java.io.Serializable;

/**
 * @author : GWFan
 * time   : 2023/4/20 19:13
 * desc   :
 */

public class CallTaskDetailsCount implements Serializable {
    private Long taskId;
    private Integer count;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
