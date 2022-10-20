package com.ipusoft.localcall.bean;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 3/26/21 10:45 AM
 * desc   : 系统通话记录
 */

public class SysCallLog implements Serializable {
    private static final long serialVersionUID = -4883035543315039346L;

    private String name;//联系人姓名
    private String phoneNumber;//联系人号码
    private long beginTime;//开始时间
    private long endTime;//结束时间
    private int duration;//时长
    private int callResult;//通话结果
    private int callType;//呼叫类型 1呼入2外呼
    private long callId;//callId
    //联动/调接口时，返回的callTime
    private String callTime;
    //主机号码
    private String hostNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCallResult() {
        return callResult;
    }

    public void setCallResult(int callResult) {
        this.callResult = callResult;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public long getCallId() {
        return callId;
    }

    public void setCallId(long callId) {
        this.callId = callId;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getHostNumber() {
        return hostNumber;
    }

    public void setHostNumber(String hostNumber) {
        this.hostNumber = hostNumber;
    }
}
