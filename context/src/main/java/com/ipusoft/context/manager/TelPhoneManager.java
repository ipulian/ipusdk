package com.ipusoft.context.manager;

import android.os.Build;
import android.telephony.TelephonyManager;

import com.ipusoft.context.AppContext;

import java.lang.reflect.Method;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * author : GWFan
 * time   : 7/21/21 4:19 PM
 * desc   :
 */

public class TelPhoneManager {

    public static String getIMEI() {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) AppContext.getAppContext().getSystemService(TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                imei = tm.getDeviceId();

            } else {
                Method method = tm.getClass().getMethod("getImei");
                method.setAccessible(true);
                imei = (String) method.invoke(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }
}
