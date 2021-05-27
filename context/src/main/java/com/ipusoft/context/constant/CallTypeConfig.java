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
    //主卡和SIP组合,默认SIP,用户可手动切换
    SIM_SIP("SIM_SIP", ""),
    //主卡和小号组合,默认小号,用户可手动切换
    SIM_X("SIM_X", "");

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

    public static List<String> getCallTypeItem(String outCallType) {
        List<String> list = new ArrayList<>();
        list.add(SIM.val);
        if (CallTypeConfig.SIM_SIP.getType().equals(outCallType)) {
            list.add(SIP.val);
        } else if (CallTypeConfig.SIM_X.getType().equals(outCallType)) {
            list.add(X.val);
        }
        return list;
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
}
