package com.ipusoft.context.bean;

import com.ipusoft.context.constant.HttpStatus;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 7/9/20 11:37 AM
 * desc   :
 */

public class VirtualNumber implements Serializable {

    private static final long serialVersionUID = 64764129134858215L;

    /**
     * 状态码
     */
    private String status = HttpStatus.SUCCESS;
    /**
     * 状态消息
     */
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String callId;
    private BindInfo bindInfo;
    private Customer customers;
    private Clue clues;
    private String device;
    private String userId;
    private int type;
    private String isClue;

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public BindInfo getBindInfo() {
        return bindInfo;
    }

    public void setBindInfo(BindInfo bindInfo) {
        this.bindInfo = bindInfo;
    }

    public Customer getCustomers() {
        return customers;
    }

    public void setCustomers(Customer customers) {
        this.customers = customers;
    }

    public Clue getClues() {
        return clues;
    }

    public void setClues(Clue clues) {
        this.clues = clues;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIsClue() {
        return isClue;
    }

    public void setIsClue(String isClue) {
        this.isClue = isClue;
    }
}

