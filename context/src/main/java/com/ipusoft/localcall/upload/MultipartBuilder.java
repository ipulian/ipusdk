package com.ipusoft.localcall.upload;

import com.amap.api.location.AMapLocation;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.bean.SysRecording;
import com.ipusoft.context.manager.IMEIManager;
import com.ipusoft.localcall.UploadFileObserve;
import com.ipusoft.localcall.bean.UploadResponse;
import com.ipusoft.logger.XLogger;
import com.ipusoft.map.LocationManager;
import com.ipusoft.utils.DateTimeUtils;
import com.ipusoft.utils.NetWorkUtils;
import com.ipusoft.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;


/**
 * author : GWFan
 * time   : 4/9/21 12:04 PM
 * desc   :
 */

public class MultipartBuilder {
    private static final String TAG = "MultipartBuilder";

    /**
     * 单文件上传
     *
     * @param sysRecording
     * @return fileUploadObserver
     */
    public static MultipartBody file2MultipartBody(SysRecording sysRecording, UploadFileObserve<UploadResponse> fileUploadObserver) {
        ArrayList<SysRecording> list = new ArrayList<>();
        if (sysRecording != null) {
            list.add(sysRecording);
        }
        return files2MultipartBody(list, fileUploadObserver);
    }

    /**
     * 多文件上传
     *
     * @param recordingFiles     文件列表
     * @param fileUploadObserver 文件上传回调
     */
    public static MultipartBody files2MultipartBody(List<SysRecording> recordingFiles,
                                                    UploadFileObserve<UploadResponse> fileUploadObserver) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        UploadFileRequestBody uploadFileRequestBody;
        for (SysRecording recording : recordingFiles) {
            String absolutePath = recording.getAbsolutePath();
            if (StringUtils.isNotEmpty(absolutePath)) {
                uploadFileRequestBody = new UploadFileRequestBody(recording, fileUploadObserver);
                builder.addFormDataPart("record", recording.getFileName(), uploadFileRequestBody);
                builder.addFormDataPart("fileMD5", recording.getFileMD5());
            }
            XLogger.d("MultipartBuilder->absolutePath：" + absolutePath);
            int callType = recording.getCallType();
            builder.addFormDataPart("callId", recording.getCallId() + "");
            builder.addFormDataPart("name", StringUtils.null2Empty(recording.getPhoneName()));
            builder.addFormDataPart("phone", recording.getPhoneNumber());
            builder.addFormDataPart("startTime", DateTimeUtils.millis2String(recording.getCallTime()));
            builder.addFormDataPart("duration", recording.getDuration() + "");
            builder.addFormDataPart("callResult", recording.getCallResult() + "");
            builder.addFormDataPart("callType", callType == 1 ? "2" : callType == 2 ? "1" : callType + "");
            builder.addFormDataPart("token", AppContext.getToken());
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
                XLogger.d("Longitude：" + location.getLongitude()
                        + "\nLatitude：" + location.getLatitude()
                        + "\nCityCode" + location.getCityCode()
                        + "\nAddress：" + location.getAddress());
            } else {
                builder.addFormDataPart("location", "");
                builder.addFormDataPart("city", "");
                builder.addFormDataPart("addr", "");
                builder.addFormDataPart("areaCode", "");
                XLogger.d("未获取到定位信息");
            }
            builder.addFormDataPart("callIP", StringUtils.null2Empty(NetWorkUtils.getIPAddress()));
            builder.addFormDataPart("callTime", StringUtils.null2Empty(recording.getCallTimeServer()));
            XLogger.d("callIP：" + StringUtils.null2Empty(NetWorkUtils.getIPAddress())
                    + "\ncallTime" + StringUtils.null2Empty(recording.getCallTimeServer()));

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

