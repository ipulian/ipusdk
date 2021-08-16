package com.ipusoft.context.bean.base;

import java.io.Serializable;

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

    //SIM，SIP，X。 其中SIP不能发送短信
    private String outCallType;

    //姓名
    private String name;

    //待联
    private String nextContactTime;

    //分类
    private String sort;

    //标签
    private String label;

    private String callId;

    private String nRecordType;

    private String recordId;

    //跟进记录
    private String followText;

    //省份
    private String province;
    //城市
    private String city;

    //来源
    private String source;

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

    public String getOutCallType() {
        return outCallType;
    }

    public void setOutCallType(String outCallType) {
        this.outCallType = outCallType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextContactTime() {
        return nextContactTime;
    }

    public void setNextContactTime(String nextContactTime) {
        this.nextContactTime = nextContactTime;
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

    public String getFollowText() {
        return followText;
    }

    public void setFollowText(String followText) {
        this.followText = followText;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
