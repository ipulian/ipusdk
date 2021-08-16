package com.ipusoft.context.bean;

/**
 * author : GWFan
 * time   : 10/22/20 9:53 AM
 * desc   : 员工操作权限
 */

public class UserAuth {
    private String custTransfer;
    private String custEdit;
    private String custShare;
    private String custDelete;
    private String custPoolEdit;
    private String custPoolTransfer;
    private String custPoolGet;
    private String custPoolDelete;

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
}
