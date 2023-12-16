package com.ipusoft.context.manager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.cache.AppCacheContext;
import com.ipusoft.localcall.bean.SIMCallOutBean;
import com.ipusoft.mmkv.datastore.CommonDataRepo;

import java.util.List;

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
            XLog.d("---->" + e.getMessage());
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

    /**
     * 通过SIM 或者 X 拨打电话
     *
     * @param phone
     */
    public static void callPhone(Context mContext, String phone, int simIndex) {
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            e.printStackTrace();
            XLog.d("---->" + e.getMessage());
            Uri uri = Uri.parse("tel:" + phone);
            intent = new Intent(Intent.ACTION_DIAL, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (simIndex == 0) {//双卡轮换
            int lastCallSim = CommonDataRepo.getLastCallSim();
            if (lastCallSim == 1) {
                simIndex = 2;
                CommonDataRepo.setLastCallSim(2);
            } else {
                simIndex = 1;
                CommonDataRepo.setLastCallSim(1);
            }
        }
        TelecomManager telecomManager = (TelecomManager) mContext.getSystemService(Context.TELECOM_SERVICE);
        TelephonyManager tm = (TelephonyManager) mContext.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
       // String caller = tm.getLine1Number();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                Log.d("callPhone", "缺少 READ_PHONE_STATE 权限");
                return;
            }
            int index = (simIndex == 2 ? 1 : 0);
            List<PhoneAccountHandle> phoneAccountHandleList = telecomManager.getCallCapablePhoneAccounts();
            if (phoneAccountHandleList != null && phoneAccountHandleList.size() > 0 && index < phoneAccountHandleList.size()) {
                intent.putExtra(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandleList.get(index));
            }
        }
        mContext.startActivity(intent);
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
        AppCacheContext.setSIMCallOutBean(new SIMCallOutBean(phone, callTime));
        callPhone(phone);
    }

    /**
     * 支持扩展字段的外呼
     *
     * @param phone
     * @param callTime
     * @param callInfo
     */
    public static void callPhoneBySim(String phone, String callTime, String callInfo) {
        XLog.d("主卡外呼->开始，phone：" + phone);
        AppCacheContext.setSIMCallOutBean(new SIMCallOutBean(phone, callTime, callInfo));
        callPhone(phone);
    }

    /**
     * 支持扩展字段的外呼，支持选择卡外呼
     * 需要获取 READ_PHONE_STATE ，READ_SMS ，READ_PHONE_NUMBERS 权限
     *
     * @param phone
     * @param callTime
     * @param callInfo
     * @param simIndex 0：卡1卡2轮换（首次使用卡1）；1：卡1外呼；2：卡2外呼
     */
    public static void callPhoneBySim(Context context, String phone, String callTime, String callInfo, int simIndex) {
        XLog.d("主卡外呼->开始，phone：" + phone);
        AppCacheContext.setSIMCallOutBean(new SIMCallOutBean(phone, callTime, callInfo));
        callPhone(context, phone, simIndex);
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
