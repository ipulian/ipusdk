package com.ipusoft.context.cache;

import android.util.Log;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.constant.PhoneState;

/**
 * author : GWFan
 * time   : 4/25/21 5:24 PM
 * desc   :
 */

public abstract class AppCacheContext extends AppContext {
    private static final String TAG = "AppCache";
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

    public static void setPhoneState(PhoneState state) {
        Log.d(TAG, "setPhoneState: ------->" + state);
        phoneState = state;
    }

    public static PhoneState getPhoneState() {
        return phoneState;
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
