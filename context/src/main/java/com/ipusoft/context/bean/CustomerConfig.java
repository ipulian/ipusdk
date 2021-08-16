package com.ipusoft.context.bean;

import com.google.gson.annotations.SerializedName;
import com.ipusoft.context.bean.base.BaseHttpResponse;

import java.util.List;

/**
 * author : GWFan
 * time   : 7/9/20 8:00 PM
 * desc   :
 */

public class CustomerConfig extends BaseHttpResponse {

    private static final long serialVersionUID = 842952689574401686L;
    private int isAdmin;
    private String globalPhoneShow;
    private List<ConfigItem> sex;
    @SerializedName("lianxi")
    private List<ConfigItem> connect;
    private List<ConfigItem> stage;
    private List<ConfigItem> sort;
    @SerializedName("fenpei")
    private List<ConfigItem> distribute;
    private List<ConfigItem> source;
    private List<ConfigItem> label;
    @SerializedName("guishu")
    private List<ConfigItem> owner;
    @SerializedName("custPool")
    private List<ConfigItem> customerPool;
    private List<ConfigItem> tag;
    private List<ConfigItem> level;
    private List<ConfigItem> industry;
    private List<ConfigItem> cluePool;
    private List<ConfigItem> customerSelectField;
    @SerializedName("WX-MESS-TYPE")
    private List<ConfigItem> weChatType;
    private UserAuth userAuth;
    private List<ConfigItem> extend1;
    private List<ConfigItem> extend2;
    private List<ConfigItem> extend3;
    private List<ConfigItem> extend4;
    private List<ConfigItem> extend5;
    private List<ConfigItem> extend6;
    private List<ConfigItem> extend7;
    private List<ConfigItem> extend8;
    private List<ConfigItem> extend9;
    private List<ConfigItem> extend10;
    private List<ConfigItem> extend11;
    private List<ConfigItem> extend12;
    private List<ConfigItem> extend13;
    private List<ConfigItem> extend14;
    private List<ConfigItem> extend15;
    private List<ConfigItem> extend16;
    private List<ConfigItem> extend17;
    private List<ConfigItem> extend18;
    private List<ConfigItem> extend19;
    private List<ConfigItem> extend20;
    private List<ConfigItem> extend21;
    private List<ConfigItem> extend22;
    private List<ConfigItem> extend23;
    private List<ConfigItem> extend24;
    private List<ConfigItem> extend25;

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getGlobalPhoneShow() {
        return globalPhoneShow;
    }

    public void setGlobalPhoneShow(String globalPhoneShow) {
        this.globalPhoneShow = globalPhoneShow;
    }

    public List<ConfigItem> getSex() {
        return sex;
    }

    public void setSex(List<ConfigItem> sex) {
        this.sex = sex;
    }

    public List<ConfigItem> getConnect() {
        return connect;
    }

    public void setConnect(List<ConfigItem> connect) {
        this.connect = connect;
    }

    public List<ConfigItem> getStage() {
        return stage;
    }

    public void setStage(List<ConfigItem> stage) {
        this.stage = stage;
    }

    public List<ConfigItem> getSort() {
        return sort;
    }

    public void setSort(List<ConfigItem> sort) {
        this.sort = sort;
    }

    public List<ConfigItem> getDistribute() {
        return distribute;
    }

    public void setDistribute(List<ConfigItem> distribute) {
        this.distribute = distribute;
    }

    public List<ConfigItem> getSource() {
        return source;
    }

    public void setSource(List<ConfigItem> source) {
        this.source = source;
    }

    public List<ConfigItem> getLabel() {
        return label;
    }

    public void setLabel(List<ConfigItem> label) {
        this.label = label;
    }

    public List<ConfigItem> getOwner() {
        return owner;
    }

    public void setOwner(List<ConfigItem> owner) {
        this.owner = owner;
    }

    public List<ConfigItem> getCustomerPool() {
        return customerPool;
    }

    public void setCustomerPool(List<ConfigItem> customerPool) {
        this.customerPool = customerPool;
    }

    public List<ConfigItem> getTag() {
        return tag;
    }

    public void setTag(List<ConfigItem> tag) {
        this.tag = tag;
    }

    public List<ConfigItem> getLevel() {
        return level;
    }

    public void setLevel(List<ConfigItem> level) {
        this.level = level;
    }

    public List<ConfigItem> getIndustry() {
        return industry;
    }

    public void setIndustry(List<ConfigItem> industry) {
        this.industry = industry;
    }

    public List<ConfigItem> getWeChatType() {
        return weChatType;
    }

    public void setWeChatType(List<ConfigItem> weChatType) {
        this.weChatType = weChatType;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public static class ConfigItem {
        private String itemCode;
        private String itemName;
        /**
         * 是否允许修改客户资料 1是0否
         */
        private String infoChange;
        /**
         * 号码显示
         * 3全显
         * 1不显******
         * 2部分显示 158****5310
         */
        private String phoneShow;

        private String type;//2：可选；3：扩展；
        private String fieldType;//字段类型：1：单行文本；2：多行文本；3：日期；4：单选框；5：多选框；6：单选按钮；7：多选按钮；
        private String useInSearch;//在搜索中使用 0：不使用；1：使用；
        private String displayArea;//显示位置 1：扩展资料；2：基本资料；3：详细资料；

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getInfoChange() {
            return infoChange;
        }

        public void setInfoChange(String infoChange) {
            this.infoChange = infoChange;
        }

        public String getPhoneShow() {
            return phoneShow;
        }

        public void setPhoneShow(String phoneShow) {
            this.phoneShow = phoneShow;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public String getUseInSearch() {
            return useInSearch;
        }

        public void setUseInSearch(String useInSearch) {
            this.useInSearch = useInSearch;
        }

        public String getDisplayArea() {
            return displayArea;
        }

        public void setDisplayArea(String displayArea) {
            this.displayArea = displayArea;
        }
    }

    public List<ConfigItem> getCluePool() {
        return cluePool;
    }

    public void setCluePool(List<ConfigItem> cluePool) {
        this.cluePool = cluePool;
    }

    public List<ConfigItem> getCustomerSelectField() {
        return customerSelectField;
    }

    public void setCustomerSelectField(List<ConfigItem> customerSelectField) {
        this.customerSelectField = customerSelectField;
    }

    public List<ConfigItem> getExtend1() {
        return extend1;
    }

    public void setExtend1(List<ConfigItem> extend1) {
        this.extend1 = extend1;
    }

    public List<ConfigItem> getExtend2() {
        return extend2;
    }

    public void setExtend2(List<ConfigItem> extend2) {
        this.extend2 = extend2;
    }

    public List<ConfigItem> getExtend3() {
        return extend3;
    }

    public void setExtend3(List<ConfigItem> extend3) {
        this.extend3 = extend3;
    }

    public List<ConfigItem> getExtend4() {
        return extend4;
    }

    public void setExtend4(List<ConfigItem> extend4) {
        this.extend4 = extend4;
    }

    public List<ConfigItem> getExtend5() {
        return extend5;
    }

    public void setExtend5(List<ConfigItem> extend5) {
        this.extend5 = extend5;
    }

    public List<ConfigItem> getExtend6() {
        return extend6;
    }

    public void setExtend6(List<ConfigItem> extend6) {
        this.extend6 = extend6;
    }

    public List<ConfigItem> getExtend7() {
        return extend7;
    }

    public void setExtend7(List<ConfigItem> extend7) {
        this.extend7 = extend7;
    }

    public List<ConfigItem> getExtend8() {
        return extend8;
    }

    public void setExtend8(List<ConfigItem> extend8) {
        this.extend8 = extend8;
    }

    public List<ConfigItem> getExtend9() {
        return extend9;
    }

    public void setExtend9(List<ConfigItem> extend9) {
        this.extend9 = extend9;
    }

    public List<ConfigItem> getExtend10() {
        return extend10;
    }

    public void setExtend10(List<ConfigItem> extend10) {
        this.extend10 = extend10;
    }

    public List<ConfigItem> getExtend11() {
        return extend11;
    }

    public void setExtend11(List<ConfigItem> extend11) {
        this.extend11 = extend11;
    }

    public List<ConfigItem> getExtend12() {
        return extend12;
    }

    public void setExtend12(List<ConfigItem> extend12) {
        this.extend12 = extend12;
    }

    public List<ConfigItem> getExtend13() {
        return extend13;
    }

    public void setExtend13(List<ConfigItem> extend13) {
        this.extend13 = extend13;
    }

    public List<ConfigItem> getExtend14() {
        return extend14;
    }

    public void setExtend14(List<ConfigItem> extend14) {
        this.extend14 = extend14;
    }

    public List<ConfigItem> getExtend15() {
        return extend15;
    }

    public void setExtend15(List<ConfigItem> extend15) {
        this.extend15 = extend15;
    }

    public List<ConfigItem> getExtend16() {
        return extend16;
    }

    public void setExtend16(List<ConfigItem> extend16) {
        this.extend16 = extend16;
    }

    public List<ConfigItem> getExtend17() {
        return extend17;
    }

    public void setExtend17(List<ConfigItem> extend17) {
        this.extend17 = extend17;
    }

    public List<ConfigItem> getExtend18() {
        return extend18;
    }

    public void setExtend18(List<ConfigItem> extend18) {
        this.extend18 = extend18;
    }

    public List<ConfigItem> getExtend19() {
        return extend19;
    }

    public void setExtend19(List<ConfigItem> extend19) {
        this.extend19 = extend19;
    }

    public List<ConfigItem> getExtend20() {
        return extend20;
    }

    public void setExtend20(List<ConfigItem> extend20) {
        this.extend20 = extend20;
    }

    public List<ConfigItem> getExtend21() {
        return extend21;
    }

    public void setExtend21(List<ConfigItem> extend21) {
        this.extend21 = extend21;
    }

    public List<ConfigItem> getExtend22() {
        return extend22;
    }

    public void setExtend22(List<ConfigItem> extend22) {
        this.extend22 = extend22;
    }

    public List<ConfigItem> getExtend23() {
        return extend23;
    }

    public void setExtend23(List<ConfigItem> extend23) {
        this.extend23 = extend23;
    }

    public List<ConfigItem> getExtend24() {
        return extend24;
    }

    public void setExtend24(List<ConfigItem> extend24) {
        this.extend24 = extend24;
    }

    public List<ConfigItem> getExtend25() {
        return extend25;
    }

    public void setExtend25(List<ConfigItem> extend25) {
        this.extend25 = extend25;
    }
}
