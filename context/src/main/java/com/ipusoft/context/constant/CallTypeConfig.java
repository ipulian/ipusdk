package com.ipusoft.context.constant;


import java.util.ArrayList;
import java.util.List;

/**
 * author : GWFan
 * time   : 3/23/21 9:57 AM
 * desc   : 呼叫方式: 主卡，小号，SIP
 */

public enum CallTypeConfig {

    SIM("SIM", "SIM卡呼叫"),
    SIP("SIP", "线路呼叫"),
    X("X", "小号呼叫"),
    TYC("TYC", "双向呼");

    private final String type;
    private final String val;

    CallTypeConfig(String type, String val) {
        this.type = type;
        this.val = val;
    }

    public String getType() {
        return type;
    }

    public String getVal() {
        return val;
    }

    public static String getValByType(String type) {
        String val = "";
        for (CallTypeConfig item : CallTypeConfig.values()) {
            if (item.getType().equals(type)) {
                val = item.getVal();
            }
        }
        return val;
    }

    public static String getTypeByVal(String val) {
        String type = "";
        for (CallTypeConfig item : CallTypeConfig.values()) {
            if (item.getVal().equals(val)) {
                type = item.getType();
            }
        }
        return type;
    }

    public static List<String> getValListByTypeList(List<String> typeList) {
        List<String> valList = new ArrayList<>();
        for (CallTypeConfig item : CallTypeConfig.values()) {
            if (typeList.contains(item.getType())) {
                valList.add(item.val);
            }
        }
        return valList;
    }
}
