package com.ipusoft.localcall.constant;

/**
 * author : GWFan
 * time   : 4/10/21 6:17 PM
 * desc   : 系统通话记录的通话类型
 */

public enum CallLogCallsType {

    INCOMING_TYPE(1, "呼入"),
    OUTGOING_TYPE(2, "外呼"),
    MISSED_TYPE(3, "未接");

    private final int type;
    private final String typeStr;

    CallLogCallsType(int type, String typeStr) {
        this.type = type;
        this.typeStr = typeStr;
    }

    public int getType() {
        return type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public static String getStrByType(int type) {
        String status = "未知状态";
        for (CallLogCallsType item : CallLogCallsType.values()) {
            if (item.getType() == type) {
                status = item.getTypeStr();
                break;
            }
        }
        return status;
    }
}
