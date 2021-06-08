package com.ipusoft.network;

/**
 * author : GWFan
 * time   : 6/8/21 9:07 AM
 * desc   :
 */

public enum NetWorkType {

    NETWORK_WIFI("WiFi"),
    NETWORK_4G("4G"),
    NETWORK_2G("2G"),
    NETWORK_3G("3G"),
    NETWORK_UNKNOWN("Unknown"),
    NETWORK_NO("No network");

    private final String desc;

    NetWorkType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
