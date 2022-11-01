package com.ipusoft.localcall.repository;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.manager.PlatformManager;
import com.ipusoft.localcall.bean.SIMCallOutBean;
import com.ipusoft.localcall.bean.SysCallLog;
import com.ipusoft.localcall.bean.UploadSysRecordingBean;
import com.ipusoft.localcall.constant.CallLogCallsType;
import com.ipusoft.localcall.constant.CallLogType;
import com.ipusoft.localcall.datastore.SimDataRepo;
import com.ipusoft.logger.XLogger;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 查询通话记录总条数
     *
     * @return
     */
    public static int queryTotalCount() {
        int count = 0;
        Cursor cursor = AppContext.getAppContext()
                .getContentResolver()
                .query(CallLog.Calls.CONTENT_URI, null, null, null,
                        null);
        if (cursor.moveToLast()) {
            count = cursor.getPosition();
        }
        cursor.close();
        return count;
    }

    /**
     * @return
     */
    public static List<SysCallLog> querySysCallLog(int page) {
        Log.d(TAG, "querySysCallLog: ------" + page);
        //" date DESC limit " + 50 + " offset " + page * 50
        Cursor cursor = AppContext.getAppContext()
                .getContentResolver()
                .query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        return getDataFormCursor(cursor);
    }

    public List<SysCallLog> querySysCallLog() {
        ArrayList<SysCallLog> list = new ArrayList<>();
        UploadSysRecordingBean uploadSysCallLog = SimDataRepo.getUploadSysCallLog();
        XLogger.d(TAG + "->querySysCallLog：开始查数据");
        long maxTime = uploadSysCallLog.getTimestamp();
        XLogger.d(TAG + "->querySysCallLog----maxTime--->" + maxTime);
        String selectionClause = CallLog.Calls.DATE + " > ? ";
        String[] selectionArgs = {Math.max(maxTime, System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000) + ""};
        XLogger.d(TAG + "->查询条件：CallLog.Calls.DATE > " + Math.max(maxTime, System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000) + "");
        Cursor cursor = AppContext.getAppContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, selectionClause, selectionArgs, CallLog.Calls.DEFAULT_SORT_ORDER);
        List<SysCallLog> sysCallLogs = getDataFormCursor(cursor);
        if (ArrayUtils.isNotEmpty(sysCallLogs)) {
            XLogger.d(TAG + "->querySysCallLog1：" + GsonUtils.toJson(sysCallLogs));
            List<SIMCallOutBean> simCallOutBeanList = SimDataRepo.getSIMCallOutBean();
            long beginTime;
            for (SysCallLog callLog : sysCallLogs) {
                beginTime = callLog.getBeginTime();
                if (beginTime > maxTime) {
                    maxTime = beginTime;
                }
                if (callLog.getCallType() == CallLogType.OUTGOING_TYPE.getType()) {
                    if (ArrayUtils.isNotEmpty(simCallOutBeanList)) {
                        XLogger.d(TAG + "->simCallOutBeanList1：" + GsonUtils.toJson(simCallOutBeanList));
                        SIMCallOutBean bean;

                        List<SIMCallOutBean> listCopy = GsonUtils.fromJson(GsonUtils.toJson(simCallOutBeanList), GsonUtils.getListType(SIMCallOutBean.class));

                        for (int i = simCallOutBeanList.size() - 1; i >= 0; i--) {
                            bean = simCallOutBeanList.get(i);
                            int timeOffset = 5 * 1000;
                            if (PlatformManager.isHUAWEI()) {
                                if (callLog.getDuration() != 0) {
                                    timeOffset = 60 * 1000;
                                }
                            }

                            /**
                             * 当有两通间隔时间非常短，但被叫相同的时候，会有bug,尤其在华为手机上，
                             * 所以这里取 timeOffset 最接近的一个。
                             */
                            if (StringUtils.equals(callLog.getPhoneNumber(), bean.getPhone()) &&
                                    Math.abs(beginTime - bean.getTimestamp()) <= timeOffset) {
                                long minOffset = Long.MAX_VALUE;
                                SIMCallOutBean target = null;
                                for (SIMCallOutBean temp : simCallOutBeanList) {
                                    long t = Math.abs(beginTime - temp.getTimestamp());
                                    if (t < minOffset) {
                                        minOffset = Math.abs(beginTime - temp.getTimestamp());
                                        target = temp;
                                    }
                                }
                                if (target != null) {
                                    boolean remove = listCopy.remove(target);
                                    if (remove) {
                                        callLog.setCallId(target.getTimestamp());
                                        callLog.setCallTime(target.getCallTime());
                                        list.add(callLog);
                                    }
                                }
                            }
                        }
                        XLogger.d(TAG + "->simCallOutBeanList2：" + GsonUtils.toJson(listCopy));
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
            XLogger.d(TAG + "->querySysCallLog1：sysCallLogs 不存在");
        }
        XLogger.d(TAG + "->callLogList：" + GsonUtils.toJson(list));
        return list;
    }

    public static List<SysCallLog> getDataFormCursor(Cursor cursor) {
        List<SysCallLog> list = new ArrayList<>();
        try {
            SysCallLog sysCallLog;
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //联系人姓名
                    String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    //联系人号码
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    //外呼开始时间
                    long beginTime = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    //通话时长
                    int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    //通话类型
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

                    //TODO
                    //本机号码可能获取不到（华为、oppo获取不到）
                    String hostNumber = cursor.getString(cursor.getColumnIndex("phone_account_address"));
                    try {
                        if (StringUtils.isEmpty(hostNumber)) {
                            /*
                             * TODO
                             * */
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
            XLogger.e(TAG + "---->" + ExceptionUtils.getErrorInfo(e));
        }
        return list;
    }

    private static String getPhoneNumber(int callLogSimId) {
        SubscriptionManager sManager = null;
        String userPhone = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            TelephonyManager tm = (TelephonyManager) AppContext.getActivityContext().getSystemService(Context.TELEPHONY_SERVICE);

            sManager = (SubscriptionManager) AppContext.getAppContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            if (ActivityCompat.checkSelfPermission(AppContext.getActivityContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //没有授权
                return userPhone;
            }
            Log.d(TAG, "getPhoneNumber: -------1=------------>" + tm.getLine1Number());
            Log.d(TAG, "getPhoneNumber: -------2=------------>" + tm.getLine1Number());

            List<SubscriptionInfo> mList = sManager.getActiveSubscriptionInfoList();//获取当前插入卡槽的卡信息
            try {
                for (SubscriptionInfo info : mList) {
                    Log.d(TAG, "getPhoneNumber: .p-0-0-0-00--------->" + GsonUtils.toJson(info));
                    int simId = info.getSubscriptionId();//获取simid 与通话记录表的simId一致
                    if (simId == callLogSimId) {
                        int slot_id = info.getSimSlotIndex();//获取卡槽位置
                        if (slot_id == 0) {
                            userPhone = tm.getLine1Number();
                        } else {
                            userPhone = tm.getLine1Number();
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                XLogger.e("getPhoneNumber-------->", e);
            }
        }
        return userPhone;
    }


}
