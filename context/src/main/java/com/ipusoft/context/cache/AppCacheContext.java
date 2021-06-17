package com.ipusoft.context.cache;

import com.ipusoft.context.AppRuntimeContext;
import com.ipusoft.context.constant.PhoneState;

/**
 * author : GWFan
 * time   : 4/25/21 5:24 PM
 * desc   :
 */

public abstract class AppCacheContext extends AppRuntimeContext {
    private static final String TAG = "AppCacheContext";
    /**
     * 主卡外呼的callId
     */
    private static long SIMCallOutCallId;

    /**
     * 主卡外呼的号码
     */
    private static String SIMCallOutNumber;

    /**
     * 当前通话状态
     */
    private static PhoneState phoneState = PhoneState.NULL;

    private static int sipState = 0;

    /**
     * @param state SIM（X）通话状态
     */
    public static void setPhoneState(PhoneState state) {
        phoneState = state;
    }

    public static PhoneState getPhoneState() {
        return phoneState;
    }

    /**
     * @param state SIP通话状态
     */
    public static void setSipState(int state) {
        sipState = state;
    }

    public static int getSipState() {
        return sipState;
    }

    public static void setSIMCallOutCallId(long simCallOutCallId) {
        AppCacheContext.SIMCallOutCallId = simCallOutCallId;
    }

    public static long getSIMCallOutCallId() {
        return SIMCallOutCallId;
    }

    public static String getSIMOutCallNumber() {
        return SIMCallOutNumber;
    }

    public static void setSIMOutCallNumber(String outCallNumber) {
        AppCacheContext.SIMCallOutNumber = outCallNumber;
    }
}
