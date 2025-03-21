package com.ipusoft.localcall.upload;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.bean.SysRecording;
import com.ipusoft.context.manager.IMEIManager;
import com.ipusoft.localcall.UploadFileObserve;
import com.ipusoft.localcall.bean.UploadResponse;
import com.ipusoft.localcall.bean.WebCallTask;
import com.ipusoft.map.LocationManager;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.oss.AliYunManager;
import com.ipusoft.utils.DateTimeUtils;
import com.ipusoft.utils.NetWorkUtils;
import com.ipusoft.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;


/**
 * author : GWFan
 * time   : 4/9/21 12:04 PM
 * desc   :
 */

public class UploadRecordMultipartBuilder {
    private static final String TAG = "UploadRecordMultipartBu";

    /**
     * 单文件上传
     *
     * @param sysRecording
     * @return fileUploadObserver
     */
    public static MultipartBody file2MultipartBody(SysRecording sysRecording, UploadFileObserve<UploadResponse> fileUploadObserver, String ossFile) {
        ArrayList<SysRecording> list = new ArrayList<>();
        if (sysRecording != null) {
            list.add(sysRecording);
        }
        return files2MultipartBody(list, fileUploadObserver, ossFile);
    }

    /**
     * 多文件上传
     *
     * @param recordingFiles     文件列表
     * @param fileUploadObserver 文件上传回调
     */
    public static MultipartBody files2MultipartBody(List<SysRecording> recordingFiles,
                                                    UploadFileObserve<UploadResponse> fileUploadObserver, String ossFile) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        UploadFileRequestBody uploadFileRequestBody;
        for (SysRecording recording : recordingFiles) {
            String absolutePath = recording.getAbsolutePath();
            String phone = recording.getPhoneNumber();
            if (StringUtils.isNotEmpty(absolutePath)) {
                // uploadFileRequestBody = new UploadFileRequestBody(recording.getAbsolutePath(), fileUploadObserver);
                builder.addFormDataPart("record", recording.getFileName());
                builder.addFormDataPart("fileMD5", recording.getFileMD5());
            }
            XLog.d("MultipartBuilder->absolutePath：" + absolutePath);
            int callType = recording.getCallType();
            builder.addFormDataPart("callId", recording.getCallId() + "");
            builder.addFormDataPart("name", StringUtils.null2Empty(recording.getPhoneName()));
            builder.addFormDataPart("phone", phone);
            builder.addFormDataPart("userPhone", StringUtils.null2Empty(recording.getCaller()));
            builder.addFormDataPart("startTime", DateTimeUtils.millis2String(recording.getCallTime()));

            Map<String, WebCallTask> taskMap = CommonDataRepo.getWebCallTaskId();
            //   Log.d(TAG, "files2MultipartBody: ---------------->" + GsonUtils.toJson(taskMap));


            WebCallTask task = taskMap.get(phone);
//            Log.d(TAG, "files2MultipartBody: ---------------3->" + phone);
//            Log.d(TAG, "files2MultipartBody: ---------------3->" + GsonUtils.toJson(task));

            try {
                if (task != null) {
                    builder.addFormDataPart("taskId", task.getTaskId());
                    CommonDataRepo.removeWebCallTaskId(phone);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Log.d(TAG, "files2MultipartBody: ---------------2->" + GsonUtils.toJson(taskMap));


            try {
                if (recording.getReleaseTime() != 0) {
                    builder.addFormDataPart("releaseTime", DateTimeUtils.millis2String(recording.getReleaseTime()));
                } else {
                    builder.addFormDataPart("releaseTime", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            builder.addFormDataPart("duration", recording.getDuration() + "");
            builder.addFormDataPart("callResult", recording.getCallResult() + "");
            builder.addFormDataPart("callType", callType == 1 ? "2" : callType == 2 ? "1" : callType + "");

            String token = AppContext.getToken();
            if (token.isEmpty()) {
                XLog.e(TAG + "->files2MultipartBody->Token is empty!");
            }
            builder.addFormDataPart("token", token);

            if (StringUtils.isNotEmpty(recording.getFileName())) {
                builder.addFormDataPart("recordUrl", "https://" + AliYunManager.BUCKET_NAME + "." + AliYunManager.OSS_END_POINT + ".aliyuncs.com/"
                        + ossFile);
                // builder.addFormDataPart("recordUrl", "https://ipufiles.oss-cn-beijing.aliyuncs.com/sim_recording/2023/05/16/477f3/%E9%83%AD%E5%8D%93%4017659055480_20230516163818.m4a");
                Log.d(TAG, "files2MultipartBody: ---------->" + "https://" + AliYunManager.BUCKET_NAME + "." + AliYunManager.OSS_END_POINT + ".aliyuncs.com/"
                        + ossFile);
            } else {
                builder.addFormDataPart("recordUrl", "");
            }

            AMapLocation location = LocationManager.getLocation();
            if (location != null) {
                builder.addFormDataPart("location", location.getLongitude() + "," + location.getLatitude());
                if (StringUtils.isNotEmpty(location.getProvince()) && StringUtils.isNotEmpty(location.getCity())) {
                    builder.addFormDataPart("city", StringUtils.null2Empty(location.getProvince())
                            + "," + StringUtils.null2Empty(location.getCity()));
                } else {
                    builder.addFormDataPart("city", "");
                }
                builder.addFormDataPart("addr", StringUtils.null2Empty(location.getAddress()));
                builder.addFormDataPart("areaCode", StringUtils.null2Empty(location.getCityCode()));
                XLog.d("Longitude：" + location.getLongitude()
                        + "\nLatitude：" + location.getLatitude()
                        + "\nCityCode" + location.getCityCode()
                        + "\nAddress：" + location.getAddress());
            } else {
                builder.addFormDataPart("location", "");
                builder.addFormDataPart("city", "");
                builder.addFormDataPart("addr", "");
                builder.addFormDataPart("areaCode", "");
                XLog.d("未获取到定位信息");
            }
            builder.addFormDataPart("callTime", StringUtils.null2Empty(recording.getCallTimeServer()));
            builder.addFormDataPart("callInfo", StringUtils.null2Empty(recording.getCallInfo()));
            if (!CommonDataRepo.getAppIsFirstInstall()) {
                //已经同意隐私政策的情况下才会去获取ip地址
                builder.addFormDataPart("callIP", StringUtils.null2Empty(NetWorkUtils.getIPAddress()));
                XLog.d("callIP：" + StringUtils.null2Empty(NetWorkUtils.getIPAddress())
                        + "\ncallTime" + StringUtils.null2Empty(recording.getCallTimeServer()));
            }

            String imei = "0";
            try {
                imei = IMEIManager.getIMEI1(AppContext.getAppContext());
                if (StringUtils.isEmpty(imei)) {
                    imei = IMEIManager.getIMEI2(AppContext.getAppContext());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                imei = "0";
            }
            builder.addFormDataPart("imei", imei);
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }
}

