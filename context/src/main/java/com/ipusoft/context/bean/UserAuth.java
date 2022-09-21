package com.ipusoft.context.bean;

/**
 * author : GWFan
 * time   : 10/22/20 9:53 AM
 * desc   : 员工操作权限
 */

public class UserAuth {
    //客户
    private String custTransfer;
    private String custEdit;
    private String custShare;
    private String custDelete;
    private String custPoolEdit;
    private String custPoolTransfer;
    private String custPoolGet;
    private String custPoolDelete;

    //会员
    private String vipEdit;//资料编辑
    private String vipTransfer;//更换会籍/教练
    private String vipDelete;//删除
    private String vipPoolEdit;//归属是客户池的时候(资料编辑)
    private String vipPoolTransfer;//更换到客户池
    private String vipPoolGet;
    private String vipPoolDelete;//归属是客户池的时候(删除)

    /**
     * userAuth.put("vipSoldCard","0");购买课程卡
     * userAuth.put("vipSoldCourse","0"); 购买私教课
     * userAuth.put("vipContinuedCard","0");续卡
     * userAuth.put("vipContinuedCourse","0");续课
     */

    private String vipSoldCard;
    private String vipSoldCourse;
    private String vipContinuedCard;
    private String vipContinuedCourse;

    public String getCustTransfer() {
        return custTransfer;
    }

    public void setCustTransfer(String custTransfer) {
        this.custTransfer = custTransfer;
    }

    public String getCustEdit() {
        return custEdit;
    }

    public void setCustEdit(String custEdit) {
        this.custEdit = custEdit;
    }

    public String getCustShare() {
        return custShare;
    }

    public void setCustShare(String custShare) {
        this.custShare = custShare;
    }

    public String getCustDelete() {
        return custDelete;
    }

    public void setCustDelete(String custDelete) {
        this.custDelete = custDelete;
    }

    public String getCustPoolEdit() {
        return custPoolEdit;
    }

    public void setCustPoolEdit(String custPoolEdit) {
        this.custPoolEdit = custPoolEdit;
    }

    public String getCustPoolTransfer() {
        return custPoolTransfer;
    }

    public void setCustPoolTransfer(String custPoolTransfer) {
        this.custPoolTransfer = custPoolTransfer;
    }

    public String getCustPoolGet() {
        return custPoolGet;
    }

    public void setCustPoolGet(String custPoolGet) {
        this.custPoolGet = custPoolGet;
    }

    public String getCustPoolDelete() {
        return custPoolDelete;
    }

    public void setCustPoolDelete(String custPoolDelete) {
        this.custPoolDelete = custPoolDelete;
    }

    public String getVipEdit() {
        return vipEdit;
    }

    public void setVipEdit(String vipEdit) {
        this.vipEdit = vipEdit;
    }

    public String getVipTransfer() {
        return vipTransfer;
    }

    public void setVipTransfer(String vipTransfer) {
        this.vipTransfer = vipTransfer;
    }

    public String getVipDelete() {
        return vipDelete;
    }

    public void setVipDelete(String vipDelete) {
        this.vipDelete = vipDelete;
    }

    public String getVipPoolEdit() {
        return vipPoolEdit;
    }

    public void setVipPoolEdit(String vipPoolEdit) {
        this.vipPoolEdit = vipPoolEdit;
    }

    public String getVipPoolTransfer() {
        return vipPoolTransfer;
    }

    public void setVipPoolTransfer(String vipPoolTransfer) {
        this.vipPoolTransfer = vipPoolTransfer;
    }

    public String getVipPoolGet() {
        return vipPoolGet;
    }

    public void setVipPoolGet(String vipPoolGet) {
        this.vipPoolGet = vipPoolGet;
    }

    public String getVipPoolDelete() {
        return vipPoolDelete;
    }

    public void setVipPoolDelete(String vipPoolDelete) {
        this.vipPoolDelete = vipPoolDelete;
    }

    public String getVipSoldCard() {
        return vipSoldCard;
    }

    public void setVipSoldCard(String vipSoldCard) {
        this.vipSoldCard = vipSoldCard;
    }

    public String getVipSoldCourse() {
        return vipSoldCourse;
    }

    public void setVipSoldCourse(String vipSoldCourse) {
        this.vipSoldCourse = vipSoldCourse;
    }

    public String getVipContinuedCard() {
        return vipContinuedCard;
    }

    public void setVipContinuedCard(String vipContinuedCard) {
        this.vipContinuedCard = vipContinuedCard;
    }

    public String getVipContinuedCourse() {
        return vipContinuedCourse;
    }

    public void setVipContinuedCourse(String vipContinuedCourse) {
        this.vipContinuedCourse = vipContinuedCourse;
    }
}
