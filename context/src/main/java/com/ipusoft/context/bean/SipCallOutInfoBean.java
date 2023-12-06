package com.ipusoft.context.bean;

import com.ipusoft.context.constant.SipPhoneType;

/**
 * author : GWFan
 * time   : 6/1/21 4:58 PM
 * desc   :
 */

public class SipCallOutInfoBean extends SipCallOutBean {
    //SIP电话类型 CALL_OUT 外呼 CALL_IN 呼入
    private SipPhoneType sipPhoneType;
    //1.客户2.线索3.陌生
    private int type;
    //来源
    private String source;
    //分类
    private String sort;
    //姓名
    private String name;
    //性别
    private String sex;
    //阶段
    private String stage;
    //标签
    private String label;

    private String virtualNumber;
    private String channel;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SipPhoneType getSipPhoneType() {
        return sipPhoneType;
    }

    public void setSipPhoneType(SipPhoneType sipPhoneType) {
        this.sipPhoneType = sipPhoneType;
    }

    public String getVirtualNumber() {
        return virtualNumber;
    }

    public void setVirtualNumber(String virtualNumber) {
        this.virtualNumber = virtualNumber;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
