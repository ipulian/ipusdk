package com.ipusoft.context.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 4/6/21 6:35 PM
 * desc   : 系统通话记录
 */

@Entity(tableName = "call_task_details")
public class CallTaskDetails implements Serializable {
    private static final long serialVersionUID = 200791783395541596L;

    @PrimaryKey()
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "task_id")
    private long taskId;

    @ColumnInfo(name = "callee")
    private String callee;

    @ColumnInfo(name = "callee_area")
    private String calleeArea;

    //0默认正常状态 1风险
    @ColumnInfo(name = "callee_status")
    private int calleeStatus;

    //0未外呼，1已外呼
    @ColumnInfo(name = "status")
    private int status;

    @ColumnInfo(name = "call_time")
    private String callTime;

    @ColumnInfo(name = "extend1")
    private int extend1;
    @ColumnInfo(name = "extend2")
    private int extend2;
    @ColumnInfo(name = "extend3")
    private int extend3;

    @ColumnInfo(name = "extend4")
    private String extend4;
    @ColumnInfo(name = "extend5")
    private String extend5;
    @ColumnInfo(name = "extend6")
    private String extend6;

    @ColumnInfo(name = "ctime")
    private String ctime;

    @ColumnInfo(name = "deleted")
    private int deleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public String getCalleeArea() {
        return calleeArea;
    }

    public void setCalleeArea(String calleeArea) {
        this.calleeArea = calleeArea;
    }

    public int getCalleeStatus() {
        return calleeStatus;
    }

    public void setCalleeStatus(int calleeStatus) {
        this.calleeStatus = calleeStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public int getExtend1() {
        return extend1;
    }

    public void setExtend1(int extend1) {
        this.extend1 = extend1;
    }

    public int getExtend2() {
        return extend2;
    }

    public void setExtend2(int extend2) {
        this.extend2 = extend2;
    }

    public int getExtend3() {
        return extend3;
    }

    public void setExtend3(int extend3) {
        this.extend3 = extend3;
    }

    public String getExtend4() {
        return extend4;
    }

    public void setExtend4(String extend4) {
        this.extend4 = extend4;
    }

    public String getExtend5() {
        return extend5;
    }

    public void setExtend5(String extend5) {
        this.extend5 = extend5;
    }

    public String getExtend6() {
        return extend6;
    }

    public void setExtend6(String extend6) {
        this.extend6 = extend6;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
