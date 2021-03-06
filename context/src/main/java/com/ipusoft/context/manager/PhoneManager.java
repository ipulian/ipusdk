package com.ipusoft.context.manager;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.cache.AppCacheContext;
import com.ipusoft.localcall.bean.SIMCallOutBean;
import com.ipusoft.logger.XLogger;

/**
 * author : GWFan
 * time   : 5/24/21 3:58 PM
 * desc   :
 */

public class PhoneManager {
    private static final String SMS_TO = "smsto:";

    private static final String SMS_BODY = "sms_body";

    /**
     * 通过SIM 或者 X 拨打电话
     *
     * @param phone
     */
    public static void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppCompatActivity currentActivity = AppContext.getActivityContext();
        if (currentActivity != null) {
            currentActivity.startActivity(intent);
        } else {
            AppContext.getAppContext().startActivity(intent);
        }
    }

    public static void callPhoneBySIP() {

    }

    /**
     * 小号外呼
     *
     * @param phone
     */
    public static void callPhoneByX(String phone) {
        callPhone(phone);
    }

    /**
     * 主卡外呼
     *
     * @param phone
     */
    public static void callPhoneBySim(String phone, String callTime) {
        XLogger.d("主卡外呼->开始，phone：" + phone);
        AppCacheContext.setSIMCallOutBean(new SIMCallOutBean(phone, callTime));
        callPhone(phone);
    }

    /**
     * 发送短息
     *
     * @param phone
     */
    public static void sendSms(String phone) {
        sendSms(phone, "");
    }

    public static void sendSms(String phone, String content) {
        Uri smsToUri = Uri.parse(SMS_TO + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra(SMS_BODY, content);
        AppCompatActivity currentActivity = AppContext.getActivityContext();
        if (currentActivity != null) {
            currentActivity.startActivity(intent);
        }
    }
}
