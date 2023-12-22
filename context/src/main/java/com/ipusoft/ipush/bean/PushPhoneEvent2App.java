package com.ipusoft.ipush.bean;

import com.ipusoft.context.bean.Clue;
import com.ipusoft.context.bean.Customer;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 5/14/21 11:56 PM
 * desc   :
 */

public class PushPhoneEvent2App implements Serializable {

    private String pushType;//CALL,SMS,LOG_NOTIFY...等
    /*
     * 1客户,2线索,3陌生
     */
    private int dataType;
    /*
     * 1SIM,2X,3SIP
     */
    private int callType;
    /*
     * 1外呼,2呼入,3发送,4接收
     */
    private int type;

    //可能是小号，也可能是真实号码
    private String phone;

    //短信内容
    private String content;

    //客户信息
    private Customer customer;

    //线索信息
    private Clue clue;

    //客户姓名
    private String cName;

    private String xPhone;

    private String xPhoneArea;

    private String virtualNumber;

    //真实号码的取号
    private String phoneArea;

    private String channelName;

    private String callId;

    private String callTime;

    private String taskType;
    private String taskId;

    private String date;

    private String versionName;
    private int versionCode;
    private String appSize;
    private String remark;
    private int isUpdate;
    private String url;
    private String channel;


    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Clue getClue() {
        return clue;
    }

    public void setClue(Clue clue) {
        this.clue = clue;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getXPhone() {
        return xPhone;
    }

    public void setXPhone(String xPhone) {
        this.xPhone = xPhone;
    }

    public String getXPhoneArea() {
        return xPhoneArea;
    }

    public void setXPhoneArea(String xPhoneArea) {
        this.xPhoneArea = xPhoneArea;
    }

    public String getVirtualNumber() {
        return virtualNumber;
    }

    public void setVirtualNumber(String virtualNumber) {
        this.virtualNumber = virtualNumber;
    }

    public String getPhoneArea() {
        return phoneArea;
    }

    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
