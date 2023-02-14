package com.ipusoft.context.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : GWFan
 * time   : 6/5/21 12:25 AM
 * desc   :
 */

public class SendSmsBean implements Serializable {
    private boolean isShow;

    //客户电话
    private String phone;

    private List<SmsModel> models;

    private Short sendType;

    private Long modelId;

    private String content;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<SmsModel> getModels() {
        return models;
    }

    public void setModels(List<SmsModel> models) {
        this.models = models;
    }

    public Short getSendType() {
        return sendType;
    }

    public void setSendType(Short sendType) {
        this.sendType = sendType;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static class SmsModel implements Serializable {
        private Long id;
        private Long cid;
        private String name;
        private String content;
        private Integer tplId;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getCid() {
            return cid;
        }

        public void setCid(Long cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getTplId() {
            return tplId;
        }

        public void setTplId(Integer tplId) {
            this.tplId = tplId;
        }
    }
}
