package com.ipusoft.localcall.repository;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.constant.DateTimePattern;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.utils.DateTimeUtils;
import com.ipusoft.utils.SysRecordingUtils;
import com.ipusoft.localcall.bean.SIMCallOutBean;
import com.ipusoft.localcall.bean.SysCallLog;
import com.ipusoft.localcall.bean.UploadSysRecordingBean;
import com.ipusoft.localcall.constant.CallLogCallsType;
import com.ipusoft.localcall.constant.CallLogType;
import com.ipusoft.localcall.datastore.SimDataRepo;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * author : GWFan
 * time   : 4/29/21 9:13 AM
 * desc   : 系统通话记录
 */

public class CallLogRepo {
    private static final String TAG = "CallLogRepo";

    private static class CallLogRepoHolder {
        private static final CallLogRepo INSTANCE = new CallLogRepo();
    }

    public static CallLogRepo getInstance() {
        return CallLogRepoHolder.INSTANCE;
    }

    public void querySysCallLog(Observer<List<SysCallLog>> observer) {
        Observable.create((ObservableOnSubscribe<List<SysCallLog>>) emitter -> {
                    Thread.sleep(5000);
                    emitter.onNext(querySysCallLog());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(observer);
    }

    /**
     * 查询通话记录总条数
     *
     * @return
     */
    public static int queryTotalCount() {
        int count = 0;
        String selectionClause = CallLog.Calls.DATE + " > ? ";
        String[] selectionArgs = {(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000) + ""};
        Cursor cursor = AppContext.getAppContext()
                .getContentResolver()
                .query(CallLog.Calls.CONTENT_URI, null, selectionClause, selectionArgs,
                        null);
//        if (cursor.moveToLast()) {
//            count = cursor.getPosition();
//        }
        count = cursor.getCount();
        return count;
    }

    /**
     * @return
     */
    public static List<SysCallLog> querySysCallLog(int page) {
        Log.d(TAG, "querySysCallLog: ------" + page);
        String paging = "date DESC ";
        String selectionClause = CallLog.Calls.DATE + " > ? ";
        String[] selectionArgs = {(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000) + ""};
        Cursor cursor = AppContext.getAppContext()
                .getContentResolver()
//                .query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
                .query(CallLog.Calls.CONTENT_URI, null, selectionClause, selectionArgs, paging);
        return getDataFormCursor(cursor, -1);
    }

    public static List<SysCallLog> querySysCallLog(int page, int pageSize) {
        Log.d(TAG, "page: ------" + page);
        String paging = CallLog.Calls.DATE + " DESC";
        String selectionClause = CallLog.Calls.DATE + " > ? ";
        String[] selectionArgs = {(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000) + ""};
        Cursor cursor = AppContext.getAppContext()
                .getContentResolver()
                .query(CallLog.Calls.CONTENT_URI, null, selectionClause, selectionArgs, paging);

        if (cursor != null) {
            int startIndex = page * pageSize;
            //int endIndex = startIndex + pageSize - 1;
            if (startIndex < cursor.getCount()) {
                cursor.move(startIndex);
            } else {
                return new ArrayList<>();
            }
        }

        return getDataFormCursor(cursor, pageSize);
    }

    public List<SysCallLog> querySysCallLog() {
        ArrayList<SysCallLog> list = new ArrayList<>();
        UploadSysRecordingBean uploadSysCallLog = SimDataRepo.getUploadSysCallLog();
        XLog.d(TAG + "->querySysCallLog：开始查数据");
        long maxTime = uploadSysCallLog.getTimestamp();
        XLog.d(TAG + "->querySysCallLog----maxTime--->" + maxTime);
        String selectionClause = CallLog.Calls.DATE + " > ? ";
        String[] selectionArgs = {Math.max(maxTime, System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000) + ""};
        XLog.d(TAG + "->查询条件：CallLog.Calls.DATE > " + Math.max(maxTime, System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000) + "");
        Cursor cursor = AppContext.getAppContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, selectionClause, selectionArgs, CallLog.Calls.DEFAULT_SORT_ORDER);
        List<SysCallLog> sysCallLogs = getDataFormCursor(cursor, -1);
        if (ArrayUtils.isNotEmpty(sysCallLogs)) {
            XLog.d(TAG + "->querySysCallLog1：\n");
            XLog.json(GsonUtils.toJson(sysCallLogs) + "\n");
            List<SIMCallOutBean> simCallOutBeanList = SimDataRepo.getSIMCallOutBean();
            XLog.d(TAG + "->simCallOutBeanList-------：\n");
            XLog.json(GsonUtils.toJson(simCallOutBeanList) + "\n");
            long beginTime;
            for (SysCallLog callLog : sysCallLogs) {
                beginTime = callLog.getBeginTime();
                if (beginTime > maxTime) {
                    maxTime = beginTime;
                }
                if (callLog.getCallType() == CallLogType.OUTGOING_TYPE.getType()) {
                    if (ArrayUtils.isNotEmpty(simCallOutBeanList)) {
                        //XLog.d(TAG + "->simCallOutBeanList1：" + GsonUtils.toJson(simCallOutBeanList));
                        SIMCallOutBean bean;

                        List<SIMCallOutBean> listCopy = GsonUtils.fromJson(GsonUtils.toJson(simCallOutBeanList), GsonUtils.getListType(SIMCallOutBean.class));

                        SIMCallOutBean target = null;
                        long minOffset = Long.MAX_VALUE;
                        for (int i = simCallOutBeanList.size() - 1; i >= 0; i--) {
                            bean = simCallOutBeanList.get(i);
                            //XLog.d(TAG + "timeOffset---------->" + timeOffset);
                            /**
                             * 当有两通间隔时间非常短，但被叫相同的电话的时候，会有bug,尤其在华为手机上，
                             * 所以这里取 timeOffset 最接近的一个。
                             */
                            if (StringUtils.equals(callLog.getPhoneNumber(), bean.getPhone())) {
                                long t = Math.abs(beginTime - bean.getTimestamp());
                                if (t < minOffset) {
                                    minOffset = Math.abs(beginTime - bean.getTimestamp());
                                    target = bean;
                                }
                            }
                                }
                                if (target != null) {
                                    boolean remove = listCopy.remove(target);
                                    if (remove) {
                                        //Log.d(TAG, "querySysCallLog: .getCallInfo------------>" + target.getCallInfo());
                                        callLog.setCallId(target.getTimestamp());
                                        callLog.setCallTime(target.getCallTime());
                                        callLog.setCallInfo(target.getCallInfo());
                                        try {
                                            if (StringUtils.isNotEmpty(target.getReleaseTime())) {
                                                callLog.setEndTime(DateTimeUtils.string2Millis(target.getReleaseTime(), DateTimePattern.getDateTimeWithSecondFormat()));
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        list.add(callLog);
                            }
                        }
                        //XLog.d(TAG + "->simCallOutBeanList2：" + GsonUtils.toJson(listCopy));
                        SimDataRepo.setSIMCallOutBean(listCopy);
                    }
                } else if (callLog.getCallType() == CallLogType.INCOMING_TYPE.getType() ||
                        callLog.getCallType() == CallLogType.MISSED_TYPE.getType() ||
                        callLog.getCallType() == CallLogType.REJECTED_TYPE.getType() ||
                        callLog.getCallType() == CallLogType.BLOCKED_TYPE.getType() ||
                        callLog.getCallType() == CallLogType.VOICEMAIL_TYPE.getType()) {
                    list.add(callLog);
                }
            }
        } else {
            XLog.d(TAG + "->querySysCallLog1：sysCallLogs 不存在");
        }
        XLog.d(TAG + "->callLogList：\n");
        XLog.json(GsonUtils.toJson(list) + "\n");
        return list;
    }

    public static List<SysCallLog> getDataFormCursor(Cursor cursor, int pageSize) {
        List<SysCallLog> list = new ArrayList<>();
        try {
            SysCallLog sysCallLog;
            if (cursor != null) {
                /**
                 * 根据机型和Android版本不同，cursor.getColumnIndex(xxx)有可能返回-1
                 */
                while (cursor.moveToNext()) {
                    if (pageSize != -1 && list.size() >= pageSize) {
                        Log.d("121200----------->", "2121212121");
                        break;
                    }
                    //联系人姓名
                    String name = "";
                    if (cursor.getColumnIndex(CallLog.Calls.CACHED_NAME) != -1) {
                        name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    }
                    //联系人号码
                    String number = "";
                    if (cursor.getColumnIndex(CallLog.Calls.NUMBER) != -1) {
                        number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    }

                    //外呼开始时间
                    long beginTime = 0;
                    if (cursor.getColumnIndex(CallLog.Calls.DATE) != -1) {
                        beginTime = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    } else {
                        Log.d(TAG, "getDataFormCursor: :---------------------->");
                    }

                    //外呼结束时间
//                    long endTime = 0;
//                    if (cursor.getColumnIndex(CallLog.Calls.DATE) != -1) {
//                        endTime = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.));
//                    } else {
//                        Log.d(TAG, "getDataFormCursor: :---------------------->");
//                    }

                    //通话时长
                    int duration = 0;
                    if (cursor.getColumnIndex(CallLog.Calls.DURATION) != -1) {
                        duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    }

                    //通话类型
                    int type = 0;
                    if (cursor.getColumnIndex(CallLog.Calls.TYPE) != -1) {
                        type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    }

                    //TODO
                    //本机号码可能获取不到（华为、oppo获取不到）
                    String hostNumber = "";
//                    try {
//                        if (cursor.getColumnIndex("phone_account_address") != -1) {
//                            hostNumber = cursor.getString(cursor.getColumnIndex("phone_account_address"));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    //TODO 可能可以获取卡槽1的手机号，但是无法获取卡槽2的手机号，目前没有任何解决方案。
                    int simIndex = 0;
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            // 当前设备的SDK版本是Android 10（API级别29）或更高
                            if (ActivityCompat.checkSelfPermission(AppContext.getActivityContext(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                                int subscriptionIdIndex = cursor.getColumnIndex(CallLog.Calls.PHONE_ACCOUNT_ID);
                                String subscriptionId = cursor.getString(subscriptionIdIndex);
                                simIndex = Integer.parseInt(subscriptionId);
                                // 使用subscriptionId来判断是哪个SIM卡
                                if (Integer.parseInt(subscriptionId) == 0) {
                                    hostNumber = getLine1Number();
                                }
                            } else {
                                XLog.d("PHONE_ACCOUNT_ID------->没有READ_CALL_LOG权限");
                            }
                        } else {
                            // 当前设备的SDK版本低于Android 10（API级别29）
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    XLog.d("这通电话是使用卡" + (simIndex + 1) + "进行外呼的");
                    XLog.d("用户设置的SIM卡号码----->SIM1--->" + CommonDataRepo.getLine1Number() + "---SIM2--->" + CommonDataRepo.getLine2Number());
                    if (StringUtils.isEmpty(hostNumber)) {
                        String phoneNumber = CommonDataRepo.getLine1Number();
                        if (simIndex == 1) {
                            phoneNumber = CommonDataRepo.getLine2Number();
                        }
                        if (StringUtils.isNotEmpty(phoneNumber)) {
                            hostNumber = phoneNumber;
                        }
                    }

                    sysCallLog = new SysCallLog();
                    sysCallLog.setName(name);
                    sysCallLog.setPhoneNumber(number);
                    sysCallLog.setBeginTime(beginTime);
                    sysCallLog.setDuration(duration);

                    hostNumber = StringUtils.null2Empty(hostNumber);
                    hostNumber = hostNumber.replace("+86", "");
                    hostNumber = hostNumber.replace("+", "");
                    sysCallLog.setHostNumber(hostNumber);
                    sysCallLog.setCallResult(0);//成功
                    if (type == CallLogType.REJECTED_TYPE.getType() || type == CallLogType.VOICEMAIL_TYPE.getType()) {
                        sysCallLog.setCallResult(1);//未接
                        sysCallLog.setDuration(0);
                    } else if (type == CallLogType.BLOCKED_TYPE.getType()) {
                        sysCallLog.setCallResult(2);//黑名单
                        sysCallLog.setDuration(0);
                    }
                    /*
                     * 时长为0，算未接通
                     */
                    if (duration == 0) {
                        sysCallLog.setCallResult(1);
                    }
                    int t = CallLogCallsType.OUTGOING_TYPE.getType();//外呼
                    if (type == CallLogType.INCOMING_TYPE.getType() || type == CallLogType.BLOCKED_TYPE.getType()
                            || type == CallLogType.MISSED_TYPE.getType() || type == CallLogType.VOICEMAIL_TYPE.getType()
                            || type == CallLogType.REJECTED_TYPE.getType()) {
                        t = CallLogCallsType.INCOMING_TYPE.getType();//呼入
                    }
                    sysCallLog.setCallType(t);
                    list.add(sysCallLog);
                }
                cursor.close();
            } else {
                Log.d(TAG, "getDataFormCursor: ======null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            XLog.e(TAG + "---->" + ExceptionUtils.getErrorInfo(e));
        }
        return list;
    }

    /**
     * 查询系统中最后一通成功的通话
     *
     * @return
     */
    public static SysCallLog getLastSuccessCall() {
        SysCallLog sysCallLog = null;
        XLog.d(TAG + "->querySysCallLog：查询最后一通成功的通话记录");
        String selectionClause = CallLog.Calls.DURATION + " > ?";
        Cursor cursor = AppContext.getAppContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, selectionClause, new String[]{"0"}, "date DESC LIMIT 1");
        List<SysCallLog> sysCallLogs = getDataFormCursor(cursor, -1);
        if (ArrayUtils.isNotEmpty(sysCallLogs)) {
            XLog.d(TAG + "->querySysCallLog 查询最后一通成功的通话记录-----：" + GsonUtils.toJson(sysCallLogs));
            sysCallLog = sysCallLogs.get(0);
        } else {
            XLog.d(TAG + "->querySysCallLog1：查询最后一通成功的通话记录----- 不存在");
        }
        return sysCallLog;
    }


    @SuppressLint("HardwareIds")
    private static String getLine1Number() {
        Application mContext = AppContext.getAppContext();
        TelecomManager telecomManager = (TelecomManager) mContext.getSystemService(Context.TELECOM_SERVICE);
        TelephonyManager tm = (TelephonyManager) mContext.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return tm.getLine1Number();
    }
}
