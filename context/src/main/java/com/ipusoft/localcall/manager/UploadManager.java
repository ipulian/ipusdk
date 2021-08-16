package com.ipusoft.localcall.manager;

import com.ipusoft.context.LiveDataBus;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.SysRecording;
import com.ipusoft.context.constant.HttpStatus;
import com.ipusoft.context.constant.LiveDataConstant;
import com.ipusoft.context.manager.ThreadPoolManager;
import com.ipusoft.localcall.UploadFileObserve;
import com.ipusoft.localcall.bean.UploadProgress;
import com.ipusoft.localcall.bean.UploadResponse;
import com.ipusoft.localcall.cache.SimAppCache;
import com.ipusoft.localcall.constant.Constant;
import com.ipusoft.localcall.constant.UploadStatus;
import com.ipusoft.localcall.module.UploadService;
import com.ipusoft.localcall.repository.SysRecordingRepo;
import com.ipusoft.logger.XLogger;
import com.ipusoft.utils.ArrayUtils;
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
            long fileSize = item.getFileSize();
            if (fileSize <= Constant.MAX_FILE_SIZE && SimAppCache.addFile2UploadTask(item)) {
                item.setUploadStatus(UploadStatus.WAIT_UPLOAD.getStatus());
                temp.add(item);
            }
        }
        addUploadTask(temp);
    }

    public void addRecording2Task(SysRecording sysRecording) {
        addRecordingList2Task(ArrayUtils.createList(sysRecording));
    }

    private void addUploadTask(List<SysRecording> list) {
        SysRecordingRepo.updateRecordingList(list, new IObserver<Boolean>() {
            @Override
            public void onNext(@NotNull @NonNull Boolean aBoolean) {
                for (SysRecording recording : list) {
                    if (recording != null) {
                        String absolutePath = recording.getAbsolutePath();
                        if (StringUtils.isNotEmpty(absolutePath)) {
                            File file = new File(recording.getAbsolutePath());
                            if (!file.exists()) {
                                XLogger.e("UploadManager：路径不为空，但是文件不存在");
                                return;
                            }
                        }
                        ThreadPoolManager.newInstance().addExecuteTask(new UploadWorker(recording));
                    }
                }
            }
        });
    }
}

class UploadWorker implements Runnable {
    private static final String TAG = "UploadWorker";
    private SysRecording sysRecording;

    private UploadWorker() {
    }

    public UploadWorker(SysRecording sysRecording) {
        this.sysRecording = sysRecording;
    }

    @Override
    public void run() {
        executeUploadHttpTask(sysRecording);
    }

    /**
     * 执行上传任务
     *
     * @param recording
     */
    private void executeUploadHttpTask(SysRecording recording) {
        UploadFileObserve<UploadResponse> uploadFileObserve = new UploadFileObserve<UploadResponse>() {
            @Override
            public void onUploadSuccess(UploadResponse responseBody) {
                String type = responseBody.getType();
                String status = responseBody.getHttpStatus();
                if (StringUtils.equals(HttpStatus.SUCCESS, status)) {
                    setUploadSucceed(type, recording);
                } else {
                    setUploadFailure(recording);
                }
                XLogger.d("onUploadSuccess：" + GsonUtils.toJson(recording) + "\n"
                        + GsonUtils.toJson(responseBody));
            }

            @Override
            public void onUploadFail(Throwable e) {
                setUploadFailure(recording);
                XLogger.e("onUploadFail：" + GsonUtils.toJson(recording) + "\n错误原因："
                        + ExceptionUtils.getErrorInfo(e));
            }

            @Override
            public void onProgress(int progress) {
                LiveDataBus.get().with(LiveDataConstant.UPLOAD_PROGRESS, UploadProgress.class).postValue(new UploadProgress(recording, progress));
            }
        };
        UploadService.Companion.uploadRecordingFile(recording, uploadFileObserve);
    }

    /**
     * 上传成功
     */
    private void setUploadSucceed(String type, SysRecording recording) {
        if (StringUtils.equals("1", type)) {
            SysRecordingRepo.deleteRecording(recording);
        } else {
            SysRecordingRepo.updateRecordingStatusByKey(recording,
                    UploadStatus.UPLOAD_SUCCEED.getStatus(),
                    new IObserver<SysRecording>() {
                        @Override
                        public void onNext(@NotNull @NonNull SysRecording sysRecording) {
                            SimAppCache.removeTaskFromQueue(sysRecording);
                        }
                    });
        }
    }

    /**
     * 上传失败
     *
     * @param recording
     */
    private void setUploadFailure(SysRecording recording) {
        recording.setRetryCount(recording.getRetryCount() + 1);
        recording.setLastRetryTime(System.currentTimeMillis());
        recording.setUploadStatus(UploadStatus.UPLOAD_FAILED.getStatus());
        SysRecordingRepo.updateRecording(recording, new IObserver<Boolean>() {
            @Override
            public void onNext(@NotNull @NonNull Boolean aBoolean) {
                SimAppCache.removeTaskFromQueue(recording);
            }
        });
    }
}
