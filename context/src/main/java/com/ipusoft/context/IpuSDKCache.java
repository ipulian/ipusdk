package com.ipusoft.context;

import com.ipusoft.context.constant.PhoneState;

/**
 * author : GWFan
 * time   : 5/30/21 5:29 PM
 * desc   :
 */

public class IpuSDKCache {
    private static PhoneState phoneState;

    public static void setPhoneState(PhoneState state) {
        phoneState = state;
    }

    public static PhoneState getPhoneState() {
        return phoneState;
    }
}
