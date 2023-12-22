package com.ipusoft.ipush.bean;

import com.ipusoft.context.bean.BindInfo;
import com.ipusoft.context.bean.CustomerCallBean;

import java.io.Serializable;

/**
 * author : GWFan
 * time   : 8/19/20 3:26 PM
 * desc   :
 */

public class WebLinkPushBody implements Serializable {

    private String type;
    private long time;
    private String msgId;
    private DataBean data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String msg;
        private String callId;
        private BindInfo bindInfo;
        private String isClue;
        private Integer isPushApp;
        private CustomerCallBean customers;
        private ClueBean clues;
        private String device;
        private String userId;
        private String status;
        private String phone;
        private String content;
        private String customerName;
        //呼叫方式 X,SIM,SIP
        private String callType;

        private String callTime;

        private String taskType;
        private String taskId;

        //上传log推送
        private String date;

        /**
         * 版本更新推送
         */
        private String versionName;
        private int versionCode;
        private Integer type;
        private String appSize;
        private String remark;
        private int isUpdate;
        private String url;
        private String channel;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

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

        public CustomerCallBean getCustomers() {
            return customers;
        }

        public void setCustomers(CustomerCallBean customers) {
            this.customers = customers;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsClue() {
            return isClue;
        }

        public void setIsClue(String isClue) {
            this.isClue = isClue;
        }

        public Integer getIsPushApp() {
            return isPushApp;
        }

        public void setIsPushApp(Integer isPushApp) {
            this.isPushApp = isPushApp;
        }

        public ClueBean getClues() {
            return clues;
        }

        public void setClues(ClueBean clues) {
            this.clues = clues;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCallType() {
            return callType;
        }

        public void setCallType(String callType) {
            this.callType = callType;
        }

        public String getCallTime() {
            return callTime;
        }

        public void setCallTime(String callTime) {
            this.callTime = callTime;
        }

        public String getTaskType() {
            return taskType;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getAppSize() {
            return appSize;
        }

        public void setAppSize(String appSize) {
            this.appSize = appSize;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getIsUpdate() {
            return isUpdate;
        }

        public void setIsUpdate(int isUpdate) {
            this.isUpdate = isUpdate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public static class ClueBean {
            private String province;
            private String city;
            private String phone;
            private String sex;
            private String name;
            private Long cluePoolId;
            private String remark;
            private Long id;
            private String source;
            private String label;
            private Integer status;

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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Long getCluePoolId() {
                return cluePoolId;
            }

            public void setCluePoolId(Long cluePoolId) {
                this.cluePoolId = cluePoolId;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

        }
    }
}