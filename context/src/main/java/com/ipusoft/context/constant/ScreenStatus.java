package com.ipusoft.context.constant;

/**
 * author : GWFan
 * time   : 2022/1/6 15:13
 * desc   : 屏幕状态
 */

public enum ScreenStatus {
    SCREEN_ON("SCREEN_ON"),
    SCREEN_OFF("SCREEN_OFF"),
    SCREEN_USER_PRESENT("SCREEN_USER_PRESENT");

    private final String desc;

    ScreenStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
