package com.ipusoft.context.manager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
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
import com.ipusoft.permission.RxPermissionUtils;
import com.ipusoft.utils.AppUtils;
import com.ipusoft.utils.SysRecordingUtils;

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
        int index = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                index = (simIndex == 2 ? 1 : 0);
                List<PhoneAccountHandle> phoneAccountHandleList = telecomManager.getCallCapablePhoneAccounts();
                if (phoneAccountHandleList != null && phoneAccountHandleList.size() > 0 && index < phoneAccountHandleList.size()) {
                    intent.putExtra(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandleList.get(index));
                }
            } else {
                XLog.d("callPhone---------->缺少 READ_PHONE_STATE 权限");
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

        try {
            //是否打开通话录音的开关
            int hasSimRecordPermission = -1;//默认：未知状态（不知道是否打开）
            if (SysRecordingUtils.isHUAWEI()) {//华为，荣耀
                hasSimRecordPermission = RxPermissionUtils.checkHuaweiRecord();
            } else if (SysRecordingUtils.isMIUI()) {//小米，红米
                hasSimRecordPermission = RxPermissionUtils.checkXiaomiRecord();
            } else if (SysRecordingUtils.isOPPO()) {//OPPO
                hasSimRecordPermission = RxPermissionUtils.checkOppoRecord();
            } else if (SysRecordingUtils.isVIVO()) {//VIVO
                hasSimRecordPermission = RxPermissionUtils.checkViVoRecord();
            } else {
                //TODO 其他机型暂未找到解决方案
            }
            XLog.d("---hasSimRecordPermission---->" + hasSimRecordPermission);

            if (hasSimRecordPermission == -1) {//未知状态

            } else if (hasSimRecordPermission == 0) {//可能未开启状态

            } else if (hasSimRecordPermission == 1) {//电话录音已经开启

            }

            boolean b = RxPermissionUtils.checkManageStoragePermission();
            if (!b) {
                //TODO 未获取文件访问权限，这里可以请提醒客户，务必打开权限，否则无法获取录音
                XLog.d("---checkManageStoragePermission---->" + b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
