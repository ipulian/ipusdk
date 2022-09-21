package com.ipusoft.context.bean;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.ipusoft.context.bean.adapter.ClueValidityAdapter;
import com.ipusoft.context.bean.base.BaseCustomerClueBean;

/**
 * author : GWFan
 * time   : 8/27/20 11:34 AM
 * desc   : 线索
 */

public class Clue extends BaseCustomerClueBean {
    //线索Id
    private Long id = 0L;
    //线索池Id
    private Long cluePoolId;
    //线索池名称
    private String cluePoolName;
    //归属用户名
    private String ownerUserName;
    //是否有效
    @SerializedName("status")
    @JsonAdapter(ClueValidityAdapter.class)
    private String validity;

    public Long getCluePoolId() {
        return cluePoolId;
    }

    public void setCluePoolId(Long cluePoolId) {
        this.cluePoolId = cluePoolId;
    }

    public String getCluePoolName() {
        return cluePoolName;
    }

    public void setCluePoolName(String cluePoolName) {
        this.cluePoolName = cluePoolName;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }
}