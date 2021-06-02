package com.ipusoft.context.manager;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.ipusoft.context.IActivityLifecycle;
import com.ipusoft.context.IpuSoftSDK;

/**
 * author : GWFan
 * time   : 5/24/21 3:58 PM
 * desc   :
 */

public class PhoneManager {
    private static final String SMS_TO = "smsto:";

    private static final String SMS_BODY = "sms_body";

    /**
     * 拨打电话
     *
     * @param phone
     */
    public static void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppCompatActivity currentActivity = IActivityLifecycle.getCurrentActivity();
        if (currentActivity != null) {
            currentActivity.startActivity(intent);
        } else {
            IpuSoftSDK.getAppContext().startActivity(intent);
        }
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
        AppCompatActivity currentActivity = IActivityLifecycle.getCurrentActivity();
        if (currentActivity != null) {
            currentActivity.startActivity(intent);
        }
    }
}
