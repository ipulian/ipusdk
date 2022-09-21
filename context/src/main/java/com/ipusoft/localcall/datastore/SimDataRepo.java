package com.ipusoft.localcall.datastore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ipusoft.localcall.bean.SIMCallOutBean;
import com.ipusoft.localcall.bean.UploadSysRecordingBean;
import com.ipusoft.localcall.constant.StorageConstant;
import com.ipusoft.mmkv.AccountMMKV;
import com.ipusoft.mmkv.CommonMMKV;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author : GWFan
 * time   : 2020/4/28 15:44
 * desc   :
 */

public class SimDataRepo {
    private static final String TAG = "SimDataRepo";

    public static void clearAllData() {
        CommonMMKV.clearDataStore();
    }

    /**
     * 上传通话记录
     *
     * @param flag
     * @param timestamp
     */
    public static void setUploadSysCallLog(boolean flag, long timestamp) {
        CommonMMKV.set(StorageConstant.UPLOAD_SYS_CALL_LOG, GsonUtils.toJson(new UploadSysRecordingBean(flag, timestamp)));
    }

    public static UploadSysRecordingBean getUploadSysCallLog() {
        String json = CommonMMKV.getString(StorageConstant.UPLOAD_SYS_CALL_LOG);
        UploadSysRecordingBean uploadSysRecordingBean;
        if (StringUtils.isEmpty(json)) {
            uploadSysRecordingBean = new UploadSysRecordingBean(true, System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            CommonMMKV.set(StorageConstant.UPLOAD_SYS_CALL_LOG, GsonUtils.toJson(uploadSysRecordingBean));
        } else {
            uploadSysRecordingBean = GsonUtils.fromJson(json, UploadSysRecordingBean.class);
            uploadSysRecordingBean.setFlag(true);
        }
        return uploadSysRecordingBean;
    }

//    /**
//     * 上传通话录音
//     *
//     * @param flag
//     * @param timestamp
//     */
//    public static void setUploadSysRecording(boolean flag, long timestamp) {
//        CommonMMKV.set(StorageConstant.UPLOAD_SYS_RECORDING, GsonUtils.toJson(new UploadSysRecordingBean(flag, timestamp)));
//    }

//    public static UploadSysRecordingBean getUploadSysRecording() {
//        String json = CommonMMKV.getString(StorageConstant.UPLOAD_SYS_RECORDING);
//        UploadSysRecordingBean uploadSysRecordingBean;
//        if (StringUtils.isEmpty(json)) {
//            uploadSysRecordingBean = new UploadSysRecordingBean(true, System.currentTimeMillis());
//            CommonMMKV.set(StorageConstant.UPLOAD_SYS_RECORDING, GsonUtils.toJson(uploadSysRecordingBean));
//        } else {
//            uploadSysRecordingBean = GsonUtils.fromJson(json, UploadSysRecordingBean.class);
//            uploadSysRecordingBean.setFlag(true);
//        }
//        return uploadSysRecordingBean;
//    }


    public static void setSIMCallOutBean(List<SIMCallOutBean> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        CommonMMKV.set(StorageConstant.SIM_OUT_CALL_BEAN, GsonUtils.toJson(list));
    }

    public static void addSIMCallOutBean(SIMCallOutBean bean) {
        List<SIMCallOutBean> list = getSIMCallOutBean();
        list.add(bean);
        CommonMMKV.set(StorageConstant.SIM_OUT_CALL_BEAN, GsonUtils.toJson(list));
    }

    public static List<SIMCallOutBean> getSIMCallOutBean() {
        String json = CommonMMKV.getString(StorageConstant.SIM_OUT_CALL_BEAN);
        ArrayList<SIMCallOutBean> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(json)) {
            list = new Gson().fromJson(json, new TypeToken<List<SIMCallOutBean>>() {
            }.getType());
        }
        if (ArrayUtils.isNotEmpty(list)) {
            Iterator<SIMCallOutBean> iterator = list.iterator();
            long currentTimeMillis = System.currentTimeMillis();
            while (iterator.hasNext()) {
                SIMCallOutBean next = iterator.next();
                Long timestamp = next.getTimestamp();
                if (timestamp == null || currentTimeMillis - timestamp > 24 * 60 * 60 * 1000) {
                    iterator.remove();
                }
            }
        }
        return list;
    }

    /**
     * 设置上次展示检查录音权限提示的时间
     *
     * @param timestamp
     */
    public static void setLastShowCheckRecordingPermissionDialog(long timestamp) {
        CommonMMKV.set(StorageConstant.LAST_SHOW_RECORDING_PERMISSION_DIALOG_TIME, timestamp);
    }

    public static long getLastShowCheckRecordingPermissionDialog() {
        return CommonMMKV.getLong(StorageConstant.LAST_SHOW_RECORDING_PERMISSION_DIALOG_TIME);
    }

    /**
     * 设置上次清除过期的通话录音的时间
     */
    public static void setLastClearOutOfDateRecordingTime(long timestamp) {
        AccountMMKV.set(StorageConstant.LAST_TIMESTAMP_FOR_CLEAR_OUT_OF_DATE_RECORDING, timestamp);
    }

    public static long getLastClearOutOfDateRecordingTime() {
        return AccountMMKV.getLong(StorageConstant.LAST_TIMESTAMP_FOR_CLEAR_OUT_OF_DATE_RECORDING, 0);
    }
}
