package com.ipusoft.localcall.manager;

import android.util.Log;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.LiveDataBus;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.SysRecording;
import com.ipusoft.context.constant.DateTimePattern;
import com.ipusoft.context.constant.LiveDataConstant;
import com.ipusoft.context.db.AppDBManager;
import com.ipusoft.context.manager.ThreadPoolManager;
import com.ipusoft.localcall.UploadFileObserve;
import com.ipusoft.localcall.bean.UploadProgress;
import com.ipusoft.localcall.bean.UploadResponse;
import com.ipusoft.localcall.cache.SimAppCache;
import com.ipusoft.localcall.constant.UploadStatus;
import com.ipusoft.localcall.module.UploadService;
import com.ipusoft.localcall.repository.SysRecordingRepo;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.oss.AliYunManager;
import com.ipusoft.oss.AliYunUploadManager;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.DateTimeUtils;
import com.ipusoft.utils.EncodeUtils;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

/**
 * author : GWFan
 * time   : 4/27/21 11:08 AM
 * desc   :
 */

public class UploadManager {
    private static final String TAG = "UploadManager";

    private static class UploadManagerHolder {
        private static final UploadManager INSTANCE = new UploadManager();
    }

    public static UploadManager getInstance() {
        return UploadManagerHolder.INSTANCE;
    }

    public void addRecordingList2Task(List<SysRecording> list) {
        if (ArrayUtils.isEmpty(list)) {
            return;
        }
        List<SysRecording> temp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SysRecording item = list.get(i);
            if (SimAppCache.addFile2UploadTask(item)) {
                item.setUploadStatus(UploadStatus.WAIT_UPLOAD.getStatus());
                item.setRetryCount(item.getRetryCount() + 1);
                temp.add(item);
            } else {
                XLog.d(TAG, "addRecordingList2Task: ------------->上传队列中已经存在");
            }
        }
        addUploadTask(temp);
    }

    public void addRecording2Task(SysRecording sysRecording) {
        addRecordingList2Task(ArrayUtils.createList(sysRecording));
    }

    private void addUploadTask(List<SysRecording> list) {

        ThreadPoolManager.newInstance().addExecuteTask(() -> {
            AppDBManager.getSysRecordingDao().updateStatusList(list);

            for (SysRecording recording : list) {
                if (recording != null) {
                    String absolutePath = recording.getAbsolutePath();
                    if (StringUtils.isNotEmpty(absolutePath)) {
                        File file = new File(recording.getAbsolutePath());
                        if (!file.exists()) {
                            XLog.e("UploadManager：路径不为空，但是文件不存在");
                        }
                    }
                    uploadRecordingFileByOSS(recording);
                    // ThreadPoolManager.newInstance().addExecuteTask(new UploadWorker(recording));
                }
            }

        });
    }

    private void uploadRecordingFileByOSS(SysRecording recording) {
        AliYunManager instance = AliYunManager.getInstance();
        instance.initAliOSS();
        String currentTime = DateTimeUtils.getCurrentTime(DateTimePattern.getDateFormat3());
        String timeMD5 = EncodeUtils.getMD5ofStr(currentTime);
        if (StringUtils.isNotEmpty(timeMD5) && timeMD5.length() >= 6) {
            timeMD5 = timeMD5.substring(0, 5);
        }

        String ossFile = "sim_recording/" + currentTime + "/" + timeMD5 + "/" + StringUtils.trim(recording.getFileName());
        if (StringUtils.isNotEmpty(recording.getAbsolutePath())) {//存在录音文件
            boolean fileExist = instance.checkFileExist(ossFile);
            Log.d(TAG, "uploadRecordingFileByOSS: .------>" + ossFile);
            if (!fileExist) {
                AliYunUploadManager.uploadFile(ossFile, recording.getAbsolutePath(), new AliYunUploadManager.OnUploadProgressListener() {
                    @Override
                    public void onProgress(long currentSize, long totalSize) {
                        int progress = (int) (currentSize * 100 / totalSize);
                        LiveDataBus.get().with(LiveDataConstant.UPLOAD_PROGRESS, UploadProgress.class).postValue(new UploadProgress(recording, progress));
                    }

                    @Override
                    public void onSuccess() {
                        XLog.d(TAG + "---onUploadSuccess：OSS----\n");
                        XLog.json(GsonUtils.toJson(recording) + "\n");
                    }

                    @Override
                    public void onFailure() {
                        XLog.e(TAG + "---onUploadFail：OSS----\n");
                        XLog.json(GsonUtils.toJson(recording) + "\n");
                    }
                });
            }
        }
        //if (!CommonDataRepo.checkUploadRecordIdExist(recording.getCallId() + "")) {
        //    Log.d(TAG, "checkUploadRecordIdExist: ------>" + recording.getCallId() + "不存在");
        //   CommonDataRepo.setUploadRecordId(recording.getCallId() + "");
        UploadFileObserve<UploadResponse> uploadFileObserve = new UploadFileObserve<UploadResponse>() {
            @Override
            public void onUploadSuccess(UploadResponse responseBody) {
                SimAppCache.removeTaskFromQueue(recording);

                int status = UploadStatus.WAIT_UPLOAD.getStatus();
                if (StringUtils.equals("1", responseBody.getType())) {//不是我的客户，或者是回电不记录 或者是 话单已上传 或者是 小号话单跳过
                    // status = UploadStatus.UPLOAD_FAILED.getStatus();
                    // if (StringUtils.isNotEmpty(responseBody.getMsg()) && responseBody.getMsg().contains("话单已上传")) {
                    status = UploadStatus.UPLOAD_IGNORE.getStatus();
                    // }
                } else {
                    status = UploadStatus.UPLOAD_SUCCEED.getStatus();
                }
                recording.setUploadStatus(status);
                recording.setUploadResult(GsonUtils.toJson(responseBody));
                SysRecordingRepo.updateRecording(recording, new IObserver<Boolean>() {
                    @Override
                    public void onNext(@NotNull @NonNull Boolean aBoolean) {

                    }
                });
                XLog.d(TAG + "---onUploadSuccess：record----\n");
                XLog.json(GsonUtils.toJson(recording) + "\n");
                XLog.json(GsonUtils.toJson(responseBody) + "\n");
            }

            @Override
            public void onUploadFail(Throwable e) {
                SimAppCache.removeTaskFromQueue(recording);

                recording.setLastRetryTime(System.currentTimeMillis());
                recording.setUploadStatus(UploadStatus.UPLOAD_FAILED.getStatus());
                recording.setUploadResult(e.getMessage());
                SysRecordingRepo.updateRecording(recording, new IObserver<Boolean>() {
                    @Override
                    public void onNext(@NotNull @NonNull Boolean aBoolean) {

                    }
                });
                XLog.e(TAG + "---onUploadFail：record----\n错误原因："
                        + ExceptionUtils.getErrorInfo(e));
                XLog.json(GsonUtils.toJson(recording) + "\n");
            }

            @Override
            public void onProgress(int progress) {
            }
        };
        UploadService.Companion.uploadRecordingFile(recording, uploadFileObserve, ossFile);
        // } else {
        //     Log.d(TAG, "checkUploadRecordIdExist: ------>" + recording.getCallId() + "已经存在了");
        // }
    }
}
