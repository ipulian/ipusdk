package com.ipusoft.context.bean;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.ipusoft.context.bean.adapter.String2LongAdapter;

/**
 * author : GWFan
 * time   : 7/13/20 1:41 PM
 * desc   : 客户
 */

public class Customer extends BaseCustomerBean {
    //客户Id
    @JsonAdapter(String2LongAdapter.class)
    @SerializedName(value = "id")
    private Long customerId;
    //紧急电话
    private String urgentPhone;
    //收藏
    private Integer collected;
    //来自企业微信
    private Integer workchated;
    //转入时间
    private String transferTime;
    //归属
    private String owner;
    //行业
    private String mIndustry;
    //是否贡献客户
    private String isSharedCustomer;
    //线索Id
    private Long clueId;
    //阶段
    private String stage;
    //待联
    private String nextContactTime;
    //分类
    private String sort;
    //归属客户池名称
    private String mPoolName;
    //等级
    private String customerLevel;
    //标记
    private String tag;
    //职位
    private String title;
    //收入
    private String salary;
    //职业
    private String job;
    //行业
    private String industry;
    //年龄
    private String age;
    //证件号码
    private String idCard;
    //公司
    private String corporation;

    //扩展字段
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
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


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIsSharedCustomer() {
        return isSharedCustomer;
    }

    public void setIsSharedCustomer(String isSharedCustomer) {
        this.isSharedCustomer = isSharedCustomer;
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

