package com.ipusoft.mmkv.datastore;

import android.util.Log;

import com.elvishew.xlog.XLog;
import com.google.gson.reflect.TypeToken;
import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.bean.LocalMessage;
import com.ipusoft.context.bean.LocalRecordPath;
import com.ipusoft.context.bean.SeatInfo;
import com.ipusoft.context.constant.CallTypeConfig;
import com.ipusoft.context.constant.DateTimePattern;
import com.ipusoft.localcall.bean.WebCallTask;
import com.ipusoft.mmkv.AccountMMKV;
import com.ipusoft.mmkv.AppMMKV;
import com.ipusoft.mmkv.CommonMMKV;
import com.ipusoft.mmkv.constant.StorageConstant;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.DateTimeUtils;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.MapUtils;
import com.ipusoft.utils.StringUtils;
import com.tencent.mmkv.MMKV;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * author : GWFan
 * time   : 6/10/21 3:26 PM
 * desc   :
 */

public class CommonDataRepo {
    private static final String TAG = "CommonDataRepo";

    public static void clearAllData() {
        CommonMMKV.clearDataStore();
    }

    public static void setToken(String token) {
        if (StringUtils.isNotEmpty(token)) {
            CommonMMKV.set(StorageConstant.TOKEN, token);
        }
    }

    public static String getToken() {
        String token;
        try {
            MMKV mmkv = MMKV.defaultMMKV();
            token = CommonMMKV.getString(StorageConstant.TOKEN);
        } catch (Exception e) {
            token = "";
            XLog.w(TAG + "->getToken123123->" + ExceptionUtils.getErrorInfo(e));
        }
        return token;
    }

    public static String getUid() {
        IAuthInfo iAuthInfo = getIAuthInfo();
        String uid = "";
        if (iAuthInfo != null) {
            uid = iAuthInfo.getUid();
        }
        return uid;
    }

    public static void setAuthInfo(AuthInfo authInfo) {
        if (authInfo != null) {
            CommonMMKV.set(StorageConstant.AUTH_INFO, GsonUtils.toJson(authInfo));
        }
    }

    public static AuthInfo getAuthInfo() {
        String json = CommonMMKV.getString(StorageConstant.AUTH_INFO);
        AuthInfo info = null;
        if (StringUtils.isNotEmpty(json)) {
            info = GsonUtils.fromJson(json, AuthInfo.class);
        }
        return info;
    }

    public static void setIAuthInfo(IAuthInfo iAuthInfo) {
        if (iAuthInfo != null && StringUtils.isNotEmpty(iAuthInfo.getToken())
                && StringUtils.isNotEmpty(iAuthInfo.getUid())) {
            CommonMMKV.set(StorageConstant.I_AUTH_INFO, GsonUtils.toJson(iAuthInfo));
        }
    }

    public static IAuthInfo getIAuthInfo() {
        String json = CommonMMKV.getString(StorageConstant.I_AUTH_INFO);
        IAuthInfo info = null;
        if (StringUtils.isNotEmpty(json)) {
            info = GsonUtils.fromJson(json, IAuthInfo.class);
        }
        return info;
    }

    /**
     * 本地通话方式
     *
     * @param str
     */
    public static void setLocalCallType(String str) {
        CommonMMKV.set(StorageConstant.LOCAL_CALL_TYPE, str);
    }

    public static String getLocalCallType() {
        return CommonMMKV.getString(StorageConstant.LOCAL_CALL_TYPE);
    }

    /**
     * 是否上传录音文件
     */
    public static void setUploadLocalRecord(boolean isUpload) {
        CommonMMKV.set(StorageConstant.UPLOAD_LOCAL_RECORD, isUpload);
    }

    public static boolean getUploadLocalRecord() {
        boolean isUpload = false;
        String localCallType = getLocalCallType();
        if (StringUtils.equals(CallTypeConfig.SIM.getType(), localCallType)) {
            isUpload = true;
        }
        return isUpload;
    }

    public static void setLocalRecordPath(String path) {
        try {
            if (StringUtils.isEmpty(path)) {
                return;
            }
            List<LocalRecordPath> result = new LinkedList<>();
            List<LocalRecordPath> list = getLocalRecordPath();
            if (ArrayUtils.isNotEmpty(list)) {
                LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
                for (LocalRecordPath localRecordPath : list) {
                    map.put(localRecordPath.getPath(), localRecordPath.getWeight());
                }
                if (map.containsKey(path)) {
                    Integer integer = map.get(path);
                    integer += 1;
                    map.put(path, integer);
                } else {
                    map.put(path, 1);
                }
                LinkedHashMap<String, Integer> sortedMap = MapUtils.sortByValue(map);
                LocalRecordPath localRecordPath;
                for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                    localRecordPath = new LocalRecordPath();
                    localRecordPath.setPath(entry.getKey());
                    localRecordPath.setWeight(entry.getValue());
                    if (result.size() < 5) {
                        result.add(localRecordPath);
                    } else {
                        int weight = result.get(result.size() - 1).getWeight();
                        if (weight <= entry.getValue()) {
                            result.add(localRecordPath);
                        }
                    }
                }
            } else {
                LocalRecordPath localRecordPath = new LocalRecordPath();
                localRecordPath.setPath(path);
                localRecordPath.setWeight(1);
                result.add(localRecordPath);
            }
            CommonMMKV.set(StorageConstant.RECORDING_FILE_PATH, GsonUtils.toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<LocalRecordPath> getLocalRecordPath() {
        String json = CommonMMKV.getString(StorageConstant.RECORDING_FILE_PATH);
        List<LocalRecordPath> localRecordPaths = new ArrayList<>();
        if (StringUtils.isNotEmpty(json)) {
            localRecordPaths = GsonUtils.fromJson(json, new TypeToken<List<LocalRecordPath>>() {
            }.getType());
        }
        return localRecordPaths;
    }

    /**
     * @param seatInfo 坐席信息
     */
    public static void setSeatInfo(SeatInfo seatInfo) {
        String str = "";
        if (seatInfo != null) {
            str = GsonUtils.toJson(seatInfo);
        }
        CommonMMKV.set(StorageConstant.SEAT_INFO, str);
    }

    /**
     * @return 坐席信息
     */
    public static SeatInfo getSeatInfo() {
        String string = CommonMMKV.getString(StorageConstant.SEAT_INFO);
        SeatInfo seatInfo = null;
        try {
            if (StringUtils.isNotEmpty(string)) {
                seatInfo = GsonUtils.fromJson(string, SeatInfo.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seatInfo;
    }

    /**
     * 保存上次获取地区数据的时间
     *
     * @param time 时间戳
     */
    public static void setGetAreaJsonTime(long time) {
        AccountMMKV.set(StorageConstant.GET_AREA_DATA_TIME, time);
    }

    public static long getGetAreaJsonTime() {
        return AccountMMKV.getLong(StorageConstant.GET_AREA_DATA_TIME);
    }

    /**
     * 返回连连员工数据
     *
     * @return
     */
    public static String getUserInfo() {
        return AccountMMKV.getString(StorageConstant.USER_INFO);
    }

    /**
     * 重新刷脸才能进入
     *
     * @param timestamp
     */
    public static void setReAuth2Login(Long timestamp) {
        AccountMMKV.set(StorageConstant.RE_AUTH_2_LOGIN_TIME_STAMP, timestamp);
    }

    public static Long getReAuth2Login() {
        return AccountMMKV.getLong(StorageConstant.RE_AUTH_2_LOGIN_TIME_STAMP, 0L);
    }

    /**
     * App是否第一次安装
     *
     * @param flag
     */
    public static void setAppIsFirstInstall(boolean flag) {
        AppMMKV.set(StorageConstant.APP_IS_FIRST_INSTALL, flag);
    }

    public static boolean getAppIsFirstInstall() {
        return AppMMKV.getBoolean(StorageConstant.APP_IS_FIRST_INSTALL, true);
    }


    public static void setShowHungUpPop(boolean showHungUpPop) {
        CommonMMKV.set(StorageConstant.SHOW_HUNG_UP_POP, showHungUpPop);
    }

    public static boolean getShowHungUpPop() {
        return CommonMMKV.getBoolean(StorageConstant.SHOW_HUNG_UP_POP, true);
    }

    public static void setShowCallOutPop(boolean showHungUpPop) {
        CommonMMKV.set(StorageConstant.SHOW_CALL_OUT_POP, showHungUpPop);
    }

    public static boolean getShowCallOutPop() {
        return CommonMMKV.getBoolean(StorageConstant.SHOW_CALL_OUT_POP, false);
    }

    public static void setSipSDKSignOut(boolean flag) {
        CommonMMKV.set(StorageConstant.SIP_SDK_SIGN_OUT, flag);
    }

    public static boolean getSipSDKSignOut() {
        return CommonMMKV.getBoolean(StorageConstant.SIP_SDK_SIGN_OUT, false);
    }

    public static void setNeverAnswerPermission(boolean flag) {
        CommonMMKV.set(StorageConstant.NEVER_ANSWER_PERMISSION, flag);
    }

    public static boolean getNeverAnswerPermission() {
        return CommonMMKV.getBoolean(StorageConstant.NEVER_ANSWER_PERMISSION, false);
    }

    /**
     * Uyou卡注册的地理位置
     *
     * @param location
     */
    public static void setInitLocation(String location) {
        CommonMMKV.set(com.ipusoft.localcall.constant.StorageConstant.LOCATION, location);
    }

    public static String getInitLocation() {
        return CommonMMKV.getString(com.ipusoft.localcall.constant.StorageConstant.LOCATION);
    }

    /**
     * Uyou卡位置异常，上次告警时间
     */
    public static void setPhoneAlarmTime(long time) {
        CommonMMKV.set(com.ipusoft.localcall.constant.StorageConstant.UYOU_ALARM_TIME, time);
    }

    public static long getPhoneAlarmTime() {
        return CommonMMKV.getLong(com.ipusoft.localcall.constant.StorageConstant.UYOU_ALARM_TIME);
    }

    //TODO
    public static void addUploadRecord(Map<String, String> record) {
        Map<String, String> uploadRecordMap = getUploadRecord();
        if (MapUtils.isEmpty(uploadRecordMap)) {
            uploadRecordMap = new HashMap<>();
        }
        uploadRecordMap.putAll(record);
        CommonMMKV.set(com.ipusoft.localcall.constant.StorageConstant.UPLOAD_RECORD, GsonUtils.toJson(uploadRecordMap));
    }

    public static Map<String, String> getUploadRecord() {
        Map<String, String> map = new HashMap<>();
        try {
            String json = CommonMMKV.getString(com.ipusoft.localcall.constant.StorageConstant.UPLOAD_RECORD);
            if (StringUtils.isNotEmpty(json)) {
                map = GsonUtils.fromJson(json, GsonUtils.getMapType(String.class, String.class));
            }
            if (MapUtils.isNotEmpty(map)) {
                Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> next = iterator.next();
                    String uploadTimeStr = next.getValue();
                    if (StringUtils.isNotEmpty(uploadTimeStr)) {
                        Date uploadTime = DateTimeUtils.string2Date(uploadTimeStr, DateTimePattern.getDateTimeWithSecondFormat());
                        Date currentTime = DateTimeUtils.getCurrentTime2(DateTimePattern.getDateTimeWithSecondFormat());
                        if (uploadTime != null) {
                            if (DateTimeUtils.dateDiff(uploadTime, currentTime) > 5 * 24 * 60 * 60 * 1000) {
                                iterator.remove();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 登录信息
     */
    public static void setLoginInfo(String json) {
        AccountMMKV.set(StorageConstant.LOGIN, json);
    }

    /**
     * 手机号码
     *
     * @param phoneNumber
     */
    public static void setLine1Number(String phoneNumber) {
        AppMMKV.set(StorageConstant.LINE1_NUMBER, phoneNumber);
    }

    public static String getLine1Number() {
        return AppMMKV.getString(StorageConstant.LINE1_NUMBER);
    }

    public static void setLine2Number(String phoneNumber) {
        AppMMKV.set(StorageConstant.LINE2_NUMBER, phoneNumber);
    }

    public static String getLine2Number() {
        return AppMMKV.getString(StorageConstant.LINE2_NUMBER);
    }

    public static void setPopWindowField(String field) {
        AppMMKV.set(StorageConstant.POP_WINDOW_FIELD, field);
    }

    public static String getPopWindowField() {
        return AppMMKV.getString(StorageConstant.POP_WINDOW_FIELD);
    }

    public static void setFingerprintAuth(boolean fingerprintAuth) {
        AppMMKV.set(StorageConstant.FINGERPRINT_AUTH, fingerprintAuth);
    }

    public static boolean getFingerprintAuth() {
        return AppMMKV.getBoolean(StorageConstant.FINGERPRINT_AUTH, false);
    }

    public static void setCallTaskModel(int model) {
        AppMMKV.set(StorageConstant.CALL_TASK_MODEL, model);
    }

    public static int getCallTaskModel() {
        return AppMMKV.getInt(StorageConstant.CALL_TASK_MODEL);
    }


    public static void setCallTaskModelRemind(boolean remind) {
        AppMMKV.set(StorageConstant.CALL_TASK_MODEL_REMIND, remind);
    }

    public static Boolean getCallTaskModelRemind() {
        return AppMMKV.getBoolean(StorageConstant.CALL_TASK_MODEL_REMIND, false);
    }

    public static void setCallTaskInterval(int interval) {
        AppMMKV.set(StorageConstant.CALL_TASK_INTERVAL, interval);
    }

    public static int getCallTaskInterval() {
        return AppMMKV.getInt(StorageConstant.CALL_TASK_INTERVAL);
    }

    public static void addNotificationMessage(LocalMessage localMessage) {
        List<LocalMessage> list = getNotificationMessage();
        if (ArrayUtils.isEmpty(list)) {
            list = new ArrayList<>();
        } else {
            Iterator<LocalMessage> iterator = list.iterator();
            while (iterator.hasNext()) {
                LocalMessage next = iterator.next();
                if (next.getType() == localMessage.getType()) {
                    iterator.remove();
                }
            }
        }
        list.add(localMessage);
        AccountMMKV.set(StorageConstant.NOTIFICATION_MESSAGE, GsonUtils.toJson(list));
    }

    public static void removeNotificationMessage(long id) {
        List<LocalMessage> list = getNotificationMessage();
        if (ArrayUtils.isEmpty(list)) {
            list = new ArrayList<>();
        } else {
            Iterator<LocalMessage> iterator = list.iterator();
            while (iterator.hasNext()) {
                LocalMessage next = iterator.next();
                if (next.getId() == id) {
                    iterator.remove();
                }
            }
        }
        AccountMMKV.set(StorageConstant.NOTIFICATION_MESSAGE, GsonUtils.toJson(list));
    }

    /**
     * @param id
     * @param read 0未读；1已读
     */
    public static void setLocalNotificationRead(long id, int read) {
        List<LocalMessage> list = getNotificationMessage();
        if (ArrayUtils.isEmpty(list)) {
            list = new ArrayList<>();
        } else {
            for (LocalMessage next : list) {
                if (next.getId() == id) {
                    next.setRead(read);
                }
            }
        }
        AccountMMKV.set(StorageConstant.NOTIFICATION_MESSAGE, GsonUtils.toJson(list));
    }

    public static List<LocalMessage> getNotificationMessage() {
        String json = AccountMMKV.getString(StorageConstant.NOTIFICATION_MESSAGE);
        List<LocalMessage> localMessage = null;
        if (StringUtils.isNotEmpty(json)) {
            localMessage = GsonUtils.fromJson(json, GsonUtils.getListType(LocalMessage.class));
        }
        return localMessage;
    }

    public static Map<String, WebCallTask> getWebCallTaskId() {
        String json = AppMMKV.getString(StorageConstant.WEB_CALL_TASK_ID);
        Map<String, WebCallTask> map = new HashMap<>();
        if (StringUtils.isNotEmpty(json)) {
            map = GsonUtils.fromJson(json, GsonUtils.getMapType(String.class, WebCallTask.class));
        }
        return map;
    }

    public static void removeWebCallTaskId(String phone) {
        try {
            Map<String, WebCallTask> taskMap = getWebCallTaskId();
            taskMap.remove(phone);
            AppMMKV.set(StorageConstant.WEB_CALL_TASK_ID, GsonUtils.toJson(taskMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setWebCallTaskId(String phone, WebCallTask webCallTask) {
        try {
            Map<String, WebCallTask> map = getWebCallTaskId();
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(phone, webCallTask);
            Iterator<Map.Entry<String, WebCallTask>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, WebCallTask> entry = iterator.next();
                WebCallTask value = entry.getValue();
                if (System.currentTimeMillis() - value.getCallTime() > 90 * 60 * 1000) {
                    iterator.remove();
                }
            }
            AppMMKV.set(StorageConstant.WEB_CALL_TASK_ID, GsonUtils.toJson(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setUploadRecordId(String callId) {
        Map<String, List<String>> map = null;
        List<String> callIdList = new ArrayList<>();
        String today = DateTimeUtils.getCurrentTime(DateTimePattern.getDateFormat());

        String json = AppMMKV.getString(StorageConstant.UPLOAD_RECORD_ID);
        if (StringUtils.isNotEmpty(json)) {
            map = GsonUtils.fromJson(json, GsonUtils.getMapType(String.class, List.class));
            if (map == null) {
                map = new HashMap<>();
            }
            if (map.containsKey(today)) {
                callIdList = map.get(today);
                if (callIdList == null) {
                    callIdList = new ArrayList<>();
                }
            } else {
                map = new HashMap<>();
            }
        } else {
            map = new HashMap<>();
        }
        callIdList.add(callId);
        map.put(today, callIdList);
        AppMMKV.set(StorageConstant.UPLOAD_RECORD_ID, GsonUtils.toJson(map));
    }


    public static boolean checkUploadRecordIdExist(String callId) {
        String json = AppMMKV.getString(StorageConstant.UPLOAD_RECORD_ID);
        if (StringUtils.isNotEmpty(json)) {
            Map<String, List<String>> map = GsonUtils.fromJson(json, GsonUtils.getMapType(String.class, List.class));
            Log.d(TAG, "checkUploadRecordIdExist: ------>" + GsonUtils.toJson(map));
            if (map == null) {
                return false;
            }
            String today = DateTimeUtils.getCurrentTime(DateTimePattern.getDateFormat());
            if (map.containsKey(today)) {
                List<String> callIdList = map.get(today);
                if (callIdList == null) {
                    return false;
                }
                return callIdList.contains(callId);
            } else {
                return false;
            }
        }
        Log.d(TAG, "checkUploadRecordIdExist: ------>没数据");
        return false;
    }

//    public static void setUploadRecordId(long callId) {
//        Map<String, List<Long>> map = null;
//        List<Long> callIdList = new ArrayList<>();
//        String today = DateTimeUtils.getCurrentTime(DateTimePattern.getDateFormat());
//
//        String json = AppMMKV.getString(StorageConstant.UPLOAD_RECORD_ID);
//        if (StringUtils.isNotEmpty(json)) {
//            map = GsonUtils.fromJson(json, GsonUtils.getMapType(String.class, List.class));
//            if (map == null) {
//                map = new HashMap<>();
//            }
//            if (map.containsKey(today)) {
//                callIdList = map.get(today);
//                if (callIdList == null) {
//                    callIdList = new ArrayList<>();
//                }
//            }
//        } else {
//            map = new HashMap<>();
//        }
//        callIdList.add(callId);
//        map.put(today, callIdList);
//        AppMMKV.set(StorageConstant.UPLOAD_RECORD_ID, GsonUtils.toJson(map));
//    }


    public static void setRemindFlag(boolean remind) {
        Map<String, Object> map = new HashMap<>();
        map.put("time", DateTimeUtils.getCurrentTime(DateTimePattern.getDateFormat()));
        map.put("value", remind);
        AppMMKV.set(StorageConstant.NO_SHOW_OPEN_RECORDING_DIALOG, GsonUtils.toJson(map));
    }

    public static boolean getRemindFlag() {
        String json = AppMMKV.getString(StorageConstant.NO_SHOW_OPEN_RECORDING_DIALOG);
        boolean result = false;
        if (StringUtils.isEmpty(json)) {
            return result;
        }
        try {
            Map<String, Object> map = GsonUtils.fromJson(json, GsonUtils.getMapType(String.class, Object.class));
            if (map != null && map.size() != 0) {
                String time = (String) map.get("time");
                boolean value = false;
                Object value1 = map.get("value");
                if (value1 != null) {
                    value = (boolean) value1;
                }
                if (StringUtils.equals(DateTimeUtils.getCurrentTime(DateTimePattern.getDateFormat()), time)) {
                    return value;
                } else {
                    return result;
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //记录上次外呼的卡
    public static void setLastCallSim(int simIndex) {
        AppMMKV.set(StorageConstant.LAST_CALL_SIM, simIndex);
    }

    public static int getLastCallSim() {
        return AppMMKV.getInt(StorageConstant.LAST_CALL_SIM);
    }

}
