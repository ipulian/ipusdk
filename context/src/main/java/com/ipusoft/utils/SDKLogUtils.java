package com.ipusoft.utils;

import android.util.Log;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.bean.AuthInfo;
import com.ipusoft.context.bean.CustomerConfig;
import com.ipusoft.context.bean.LocalRecordPath;
import com.ipusoft.context.bean.SysRecording;
import com.ipusoft.context.constant.DateTimePattern;
import com.ipusoft.context.db.AppDBManager;
import com.ipusoft.context.manager.ThreadPoolManager;
import com.ipusoft.localcall.constant.UploadStatus;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.mmkv.datastore.CustomerConfigRepo;
import com.ipusoft.oss.AliYunUploadManager;

import java.io.File;
import java.util.List;

/**
 * @author : GWFan
 * time   : 2023/8/3 15:25
 * desc   :
 */

public class SDKLogUtils {
    /**
     * 打包上传故障信息
     *
     * @param descInfo 描述信息
     * @param listener 上传结果
     */
    public static void uploadLogInfo(String descInfo, String dateTime, AliYunUploadManager.OnUploadProgressListener listener) {
//        ObservableField<String> value = valueLiveData.getValue();
        String logPath = FileUtilsKt.getLogPath(AppContext.getAppContext());
        String logTempPath = FileUtilsKt.getLogTempPath(AppContext.getAppContext());
        // if (value != null) {
        //     String dateTime = value.get();
        if (StringUtils.isNotEmpty(dateTime)) {
            String[] s = dateTime.split(" ");
            //log日志
            File logFile = new File(logPath + File.separator + s[0] + ".txt");
            //描述文件
            File descFile = new File(logTempPath + File.separator + s[0] + "_desc.info");

            List<LocalRecordPath> list = CommonDataRepo.getLocalRecordPath();

            String systemLanguage = DeviceUtils.getSystemLanguage();
            String systemVersion = DeviceUtils.getSystemVersion();
            String systemModel = DeviceUtils.getSystemModel();
            String systemDevice = DeviceUtils.getSystemDevice();
            String deviceBrand = DeviceUtils.getDeviceBrand();
            String deviceManufacturer = DeviceUtils.getDeviceManufacturer();
            String appName = AppUtils.getAppName();
            String pkgName = AppUtils.getAppPackageName();
            String appVersionName = AppUtils.getAppVersionName();

            AuthInfo authInfo = AppContext.getAuthInfo();

            FileIOUtils.writeFileFromString(descFile,
                    "系统语言：" + systemLanguage
                            + "\n系统版本：" + systemVersion
                            + "\n手机型号：" + systemModel
                            + "\n设备名称：" + systemDevice
                            + "\n手机厂商：" + deviceBrand
                            + "\n手机厂商名称：" + deviceManufacturer
                            + "\n版本名称：" + appVersionName
                            + "\n应用包名：" + pkgName
                            + "\n应用版本号：" + appName
                            + "\n渠道：" + AppUtils.getAppName()
                            + "\n版本类型：SDK"
                            + "\n登陆用户信息：" + GsonUtils.toJson(authInfo)
                            + "\n当前版本号：" + AppUtils.getAppVersionName()
                            + "\n故障发生时间：" + dateTime
                            + "\n问题描述：" + descInfo
                            + "\n系统录音保存路径：" + GsonUtils.toJson(list) + "\n");

//            ThreadPoolManager.newInstance().addExecuteTask(() -> {
//                List<SysRecording> list1 = AppDBManager.getSysRecordingDao().queryAll(
//                        ArrayUtils.createList(
//                                UploadStatus.WAIT_UPLOAD.getStatus(),
//                                UploadStatus.UPLOAD_SUCCEED.getStatus(),
//                                UploadStatus.UPLOADING.getStatus(),
//                                UploadStatus.UPLOAD_FAILED.getStatus()));


            String zipFileName = dateTime + "-" + authInfo.getKey() + "----" +
                    authInfo.getUsername() + "-" + "---SDK.zip";
            //压缩文件
            File zipFile = new File(logTempPath + File.separator + zipFileName);
            ZipUtils.zipFolder(zipFile, logFile, descFile);
            AliYunUploadManager.uploadFile("sim_sdk_log/" + DateTimeUtils.getCurrentTime(DateTimePattern.getDateFormat()) + "/" + zipFileName, zipFile.getAbsolutePath(), listener);

            //   });
        }
        //  }
    }
}
