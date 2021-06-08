package com.ipusoft.context.bean.base;

import java.io.Serializable;
import java.util.List;

/**
 * author : GWFan
 * time   : 6/4/21 10:35 AM
 * desc   :
 */

public abstract class BaseCallBean extends FloatingWindowStatus implements Serializable {
    //客户电话
    private String cPhone;
    //隐藏后的号码
    private String hPhone;
    //小号
    private String xPhone;
    private String virtualNumber;
    /**
     * 客户号码归属地
     */
    private String phoneArea;
    /**
     * 小号归属地
     */
    private String XPhoneArea;
    /**
     * 小号渠道
     */
    private String channelName;
    //标签
    private List<String> labelList;
    //阶段
    private List<String> stageList;

    //分类
    private List<String> sortList;

    //SIM，SIP，X。 其中SIP不能发送短信
    private String outCallType;

    //姓名
    private String name;

    //待联
    private String nextConnect;

    //阶段
    private String stage;

    //分类
    private String sort;

    //标签
    private String label;

    private String callId;

    private String nRecordType;

    private String recordId;

    private String followUp;

    public String getCPhone() {
        return cPhone;
    }

    public void setCPhone(String cPhone) {
        this.cPhone = cPhone;
    }

    public String getHPhone() {
        return hPhone;
    }

    public void setHPhone(String hPhone) {
        this.hPhone = hPhone;
    }

    public String getXPhone() {
        return xPhone;
    }

    public void setXPhone(String xPhone) {
        this.xPhone = xPhone;
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

    public String getXPhoneArea() {
        return XPhoneArea;
    }

    public void setXPhoneArea(String XPhoneArea) {
        this.XPhoneArea = XPhoneArea;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public String getOutCallType() {
        return outCallType;
    }

    public void setOutCallType(String outCallType) {
        this.outCallType = outCallType;
    }

    public List<String> getStageList() {
        return stageList;
    }

    public void setStageList(List<String> stageList) {
        this.stageList = stageList;
    }

    public List<String> getSortList() {
        return sortList;
    }

    public void setSortList(List<String> sortList) {
        this.sortList = sortList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextConnect() {
        return nextConnect;
    }

    public void setNextConnect(String nextConnect) {
        this.nextConnect = nextConnect;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getNRecordType() {
        return nRecordType;
    }

    public void setNRecordType(String nRecordType) {
        this.nRecordType = nRecordType;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getFollowUp() {
        return followUp;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }
}
