package com.ipusoft.context.bean;

import com.google.gson.annotations.SerializedName;
import com.ipusoft.context.bean.base.BaseHttpResponse;

/**
 * author : GWFan
 * time   : 7/13/20 1:41 PM
 * desc   :
 */

public class Customer extends BaseHttpResponse {
    private static final long serialVersionUID = 9007586325843529149L;
    private String name;
    private Integer sex;
    private String mSex;
    private String stage;
    private String phone;
    private String urgentPhone;
    private Integer collected;
    private Integer lastCallStatus;
    private String nextContactTime;
    private Integer workchated;
    private Long id;
    private String picUrl;
    private String sort;
    private String source;
    private String province;
    private String city;
    private String age;
    private String remark;
    private String idCard;
    private String corporation;
    private String label;
    private String job;
    private String transferTime;
    private String lastCallTime;
    private String lastCallDirect;
    private String owner;
    private String industry;
    private String mIndustry;
    @SerializedName("customerLevel")
    private String level;
    private int ownerType;
    private String title;//职位
    private String salary;
    private Long ownerId;
    private String tag;//标记
    private String area;
    private String isSharedCustomer;
    private String mTag;
    private Long clueId;
    private String mPoolName;
    private String extend1;
    private String extend2;
    private String extend3;
    private String extend4;
    private String extend5;
    private String extend6;
    private String extend7;
    private String extend8;
    private String extend9;
    private String extend10;
    private String extend11;
    private String extend12;
    private String extend13;
    private String extend14;
    private String extend15;
    private String extend16;
    private String extend17;
    private String extend18;
    private String extend19;
    private String extend20;
    private String extend21;
    private String extend22;
    private String extend23;
    private String extend24;
    private String extend25;

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

    public String getmSex() {
        return mSex;
    }

    public void setmSex(String mSex) {
        this.mSex = mSex;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrgentPhone() {
        return urgentPhone;
    }

    public void setUrgentPhone(String urgentPhone) {
        this.urgentPhone = urgentPhone;
    }

    public Integer getCollected() {
        return collected;
    }

    public void setCollected(Integer collected) {
        this.collected = collected;
    }

    public Integer getLastCallStatus() {
        return lastCallStatus;
    }

    public void setLastCallStatus(Integer lastCallStatus) {
        this.lastCallStatus = lastCallStatus;
    }

    public String getNextContactTime() {
        return nextContactTime;
    }

    public void setNextContactTime(String nextContactTime) {
        this.nextContactTime = nextContactTime;
    }


    public Integer getWorkchated() {
        return workchated;
    }

    public void setWorkchated(Integer workchated) {
        this.workchated = workchated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getmIndustry() {
        return mIndustry;
    }

    public void setmIndustry(String mIndustry) {
        this.mIndustry = mIndustry;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIsSharedCustomer() {
        return isSharedCustomer;
    }

    public void setIsSharedCustomer(String isSharedCustomer) {
        this.isSharedCustomer = isSharedCustomer;
    }

    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public Long getClueId() {
        return clueId;
    }

    public void setClueId(Long clueId) {
        this.clueId = clueId;
    }

    public String getmPoolName() {
        return mPoolName;
    }

    public void setmPoolName(String mPoolName) {
        this.mPoolName = mPoolName;
    }
}

