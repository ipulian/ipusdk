package com.ipusoft.context.bean.base;

import com.google.gson.annotations.JsonAdapter;
import com.ipusoft.context.bean.adapter.String2IntegerAdapter;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 7/23/21 2:47 PM
 * desc   : 客户和线索的公共字段
 */

public class BaseCustomerClueBean implements Serializable {
    //省份
    private String province;
    //城市
    private String city;
    //头像地址
    private String picUrl;
    //姓名
    private String name;
    //性别
    @JsonAdapter(String2IntegerAdapter.class)
    private Integer sex;

    private String mSex;
    //电话
    private String phone;
    //来源
    private String source;
    //归属类型
    private Integer ownerType;
    //归属Id
    private Long ownerId;
    //备注
    private String remark;
    //标签
    private String label;
    //上次呼叫状态
    private Integer lastCallStatus;
    //上次联系时间
    private String lastCallTime;
    //上次呼叫类型(外呼，呼入)
    private String lastCallDirect;

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMSex() {
        return mSex;
    }

    public void setMSex(String mSex) {
        this.mSex = mSex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getLastCallStatus() {
        return lastCallStatus;
    }

    public void setLastCallStatus(Integer lastCallStatus) {
        this.lastCallStatus = lastCallStatus;
    }

    public String getLastCallTime() {
        return lastCallTime;
    }

    public void setLastCallTime(String lastCallTime) {
        this.lastCallTime = lastCallTime;
    }

    public String getLastCallDirect() {
        return lastCallDirect;
    }

    public void setLastCallDirect(String lastCallDirect) {
        this.lastCallDirect = lastCallDirect;
    }
}
