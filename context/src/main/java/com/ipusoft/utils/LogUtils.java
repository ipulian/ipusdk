package com.ipusoft.utils;

import android.util.Log;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.bean.SeatInfo;
import com.ipusoft.context.constant.DateTimePattern;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.oss.AliYunManager;
import com.ipusoft.oss.AliYunUploadManager;

/**
 * @author : GWFan
 * time   : 2023/2/15 09:00
 * desc   :
 */

public class LogUtils {

    private static final String TAG = "LogUtils";

    public static void uploadSIPLog() {
        AliYunManager.getInstance().initAliOSS();

        new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String logPath = FileUtilsKt.getLogPath(AppContext.getAppContext());
                if (StringUtils.isNotEmpty(logPath)) {
                    Log.d(TAG, "uploadSIPLog: " + logPath);
                    String filePath = logPath + "/" + DateTimeUtils.getCurrentTime(DateTimePattern.getDateFormat());
                    SeatInfo seatInfo = CommonDataRepo.getSeatInfo();
                    StringBuilder objectName = new StringBuilder(DateTimeUtils.getCurrentTime(DateTimePattern.getDateTimeWithSecondFormat()));
                    if (seatInfo != null) {
                        objectName.append(seatInfo.getSeatNo()).append(seatInfo.getPassword());
                    }
                    AliYunUploadManager.uploadFile("sip_sdk_log/" + objectName, filePath, null);
                }
            }
        }.start();
    }
}
