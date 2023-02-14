package com.ipusoft.context.manager;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.cache.AppCacheContext;
import com.ipusoft.context.view.dialog.IDialog;
import com.ipusoft.localcall.bean.SIMCallOutBean;
import com.elvishew.xlog.XLog;
import com.ipusoft.permission.RxPermissionUtils;

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
        //   AppCompatActivity currentActivity = AppContext.getActivityContext();
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            e.printStackTrace();
            Uri uri = Uri.parse("tel:" + phone);
            intent = new Intent(Intent.ACTION_DIAL, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
//        if (currentActivity != null) {
//            currentActivity.startActivity(intent);
//        } else {
        AppContext.getAppContext().startActivity(intent);
//        }
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
        XLog.d("主卡外呼->开始，phone：" + phone);
        boolean hasSimRecordPermission = true;
        try {
            if (PlatformManager.isHUAWEI()) {
                hasSimRecordPermission = RxPermissionUtils.checkHuaweiRecord();
            } else if (PlatformManager.isMIUI()) {
                hasSimRecordPermission = RxPermissionUtils.checkXiaomiRecord();
            } else if (PlatformManager.isOPPO()) {
                hasSimRecordPermission = RxPermissionUtils.checkOppoRecord();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        XLog.d("主卡外呼->开始，检查主卡通话录音权限：" + hasSimRecordPermission);
        if (hasSimRecordPermission) {
            AppCacheContext.setSIMCallOutBean(new SIMCallOutBean(phone, callTime));
            callPhone(phone);
        } else {
            IDialog.getInstance()
                    .setTitle("未打开通话录音功能")
                    .setMsg("可能会影响通话录音")
                    .setConfirmText("继续拨打")
                    .setCancelText("去打开")
                    .setOnCancelClickListener(() -> {
                        if (PlatformManager.isHUAWEI()) {
                            PlatformManager.startHuaweiRecord();
                        } else if (PlatformManager.isMIUI()) {
                            PlatformManager.startXiaomiRecord();
                        } else if (PlatformManager.isOPPO()) {
                            PlatformManager.startOppoRecord();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            AppContext.getActivityContext().startActivity(intent);
                        }
                    }).setOnConfirmClickListener(() -> {
                        AppCacheContext.setSIMCallOutBean(new SIMCallOutBean(phone, callTime));
                        callPhone(phone);
                    })
                    .show();
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
        AppCompatActivity currentActivity = AppContext.getActivityContext();
        if (currentActivity != null) {
            currentActivity.startActivity(intent);
        }
    }
}
