package com.ipusoft.mmkv.datastore;

import com.google.gson.reflect.TypeToken;
import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.bean.LocalRecordPath;
import com.ipusoft.context.bean.SeatInfo;
import com.ipusoft.context.constant.CallTypeConfig;
import com.ipusoft.logger.XLogger;
import com.ipusoft.mmkv.AccountMMKV;
import com.ipusoft.mmkv.AppMMKV;
import com.ipusoft.mmkv.CommonMMKV;
import com.ipusoft.mmkv.constant.StorageConstant;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.MapUtils;
import com.ipusoft.utils.StringUtils;

import java.util.ArrayList;
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
        return CommonMMKV.getString(StorageConstant.TOKEN);
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
        XLogger.d(TAG + "---修改本地通话方式--->" + str);
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
        } else if (StringUtils.equals(CallTypeConfig.SIP.getType(), localCallType)) {
            isUpload = false;
        } else if (StringUtils.equals(CallTypeConfig.X.getType(), localCallType)) {
            isUpload = CommonMMKV.getBoolean(StorageConstant.UPLOAD_LOCAL_RECORD, false);
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
}
