package com.ipusoft.oss;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.SysRecording;
import com.ipusoft.context.constant.DateTimePattern;
import com.ipusoft.context.iface.OnMyValueClickListener;
import com.ipusoft.localcall.UploadFileObserve;
import com.ipusoft.localcall.bean.SysCallLog;
import com.ipusoft.localcall.bean.UploadResponse;
import com.ipusoft.localcall.cache.SimAppCache;
import com.ipusoft.localcall.constant.UploadStatus;
import com.ipusoft.localcall.module.UploadService;
import com.ipusoft.localcall.repository.FileRepository;
import com.ipusoft.localcall.repository.SysRecordingRepo;
import com.ipusoft.utils.DateTimeUtils;
import com.ipusoft.utils.EncodeUtils;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.FileUtilsKt;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;
import com.ipusoft.utils.ThreadUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;

/**
 * author : GWFan
 * time   : 8/3/21 5:33 PM
 * desc   :
 */

public class AliYunUploadManager extends AliYunManager {
    private static final String TAG = "AliYunUploadManager";

    public interface OnUploadProgressListener {
        //进度
        void onProgress(long currentSize, long totalSize);

        //成功
        void onSuccess();

        //失败
        void onFailure();
    }

    /**
     * 上传文件
     *
     * @param object
     * @param filepath
     */
    public static void uploadFile(String object, String filepath, OnUploadProgressListener listener) {
        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(BUCKET_NAME, object, filepath);
        // 异步上传时可以设置进度回调。
        put.setProgressCallback((request, currentSize, totalSize) -> {
            if (listener != null) {
                listener.onProgress(currentSize, totalSize);
            }
        });
        Log.d("TAG", "uploadLogInfo: -----------3");
        mOSS.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                XLog.d("AliYunUploadManager：" + filepath);
                Log.d("TAG", "uploadLogInfo: -----------4");
                if (listener != null) {
                    listener.onSuccess();
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常。
                if (clientException != null) {
                    // 本地异常，如网络异常等。
                    clientException.printStackTrace();
                    XLog.e(TAG + "->clientThreadPoolExecutorException->" + clientException);
                }
                if (serviceException != null) {
                    // 服务异常。
                    XLog.e(TAG + "->serviceException->" + serviceException);
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
        // task.cancel(); // 可以取消任务。
// task.waitUntilFinished(); // 等待任务完成。
    }


    /**
     * 上传文件
     *
     * @param object
     * @param filepath
     */
    public static void uploadFile(String bucketName, String object, String filepath, OnUploadProgressListener listener) {
        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(bucketName, object, filepath);
        // 异步上传时可以设置进度回调。
        put.setProgressCallback((request, currentSize, totalSize) -> {
            if (listener != null) {
                listener.onProgress(currentSize, totalSize);
            }
        });

        mOSS.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                XLog.d("AliYunUploadManager：" + GsonUtils.toJson(result));
                if (listener != null) {
                    listener.onSuccess();
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常。
                if (clientException != null) {
                    // 本地异常，如网络异常等。
                    clientException.printStackTrace();
                    XLog.e(TAG + "->clientThreadPoolExecutorException->" + clientException);
                }
                if (serviceException != null) {
                    // 服务异常。
                    XLog.e(TAG + "->serviceException->" + serviceException);
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
        // task.cancel(); // 可以取消任务。
// task.waitUntilFinished(); // 等待任务完成。
    }

    public static String generateObjectName(String fileName) {
        String currentTime = DateTimeUtils.getCurrentTime(DateTimePattern.getDateFormat3());
        String timeMD5 = EncodeUtils.getMD5ofStr(currentTime);
        if (StringUtils.isNotEmpty(timeMD5) && timeMD5.length() >= 6) {
            timeMD5 = timeMD5.substring(0, 5);
        }
        return "sim_recording/" + currentTime + "/" + timeMD5 + "/" + StringUtils.trim(fileName);
    }


    public static void uploadRecording(SysCallLog callLog, File file) {
        //上传服务端需要用到的Bean SysRecording
        SysRecording recording = new SysRecording();
        recording.setDuration(callLog.getDuration());
        recording.setCallTime(callLog.getBeginTime());
        recording.setPhoneName(callLog.getName());
        recording.setPhoneNumber(callLog.getPhoneNumber());
        recording.setCallType(callLog.getCallType());
        recording.setCallResult(callLog.getCallResult());
        recording.setCallTimeServer(callLog.getCallTime());
        if (callLog.getCallId() != 0) {
            recording.setCallId(callLog.getCallId());
        } else {
            recording.setCallId(System.currentTimeMillis());
        }
        recording.setUploadStatus(UploadStatus.WAIT_UPLOAD.getStatus());

        File nFile = new File(FileUtilsKt.getAudioPath(AppContext.getAppContext()) + "/" + file.getName());
        //将录音文件的文件名，文件生成时间，文件大小，文件的MD5存到 SysRecording 中，一并上传到服务端
        recording.setAbsolutePath(nFile.getAbsolutePath());
        recording.setFileName(file.getName());
        recording.setFileGenerateTime(file.lastModified());
        recording.setFileSize(file.length());
        recording.setFileMD5(FileUtilsKt.getFileMD5ToString(file));
        Map<File, File> map = new HashMap<>();
        map.put(file, nFile);
        //拷贝录音文件到App目录中
        FileRepository.copyFileAsync(map);
        String object = AliYunUploadManager.generateObjectName(file.getName());
        AliYunManager aliYunManager = AliYunManager.getInstanceAndInit();
        boolean fileExist = aliYunManager.checkFileExist(object);
        XLog.d(TAG + "---文件已经存在于OSS中---->" + fileExist);
        if (!fileExist) {
            AliYunUploadManager.uploadFile(object, file.getAbsolutePath(),
                    new AliYunUploadManager.OnUploadProgressListener() {
                        @Override
                        public void onProgress(long currentSize, long totalSize) {

                        }

                        @Override
                        public void onSuccess() {
                            //文件上传成功
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
        } else {
            //文件已经存在OSS中
        }

        UploadFileObserve<UploadResponse> uploadFileObserve = new UploadFileObserve<UploadResponse>() {
            @Override
            public void onUploadSuccess(UploadResponse responseBody) {
                XLog.d(TAG + "onUploadSuccess：话单：" + GsonUtils.toJson(recording) + "\n返回结果："
                        + GsonUtils.toJson(responseBody));
                if (StringUtils.equals("1", responseBody.getType())) {
                    Log.d(TAG, responseBody.getMsg());
                }
            }

            @Override
            public void onUploadFail(Throwable e) {
                XLog.e(TAG + "onUploadFail：" + GsonUtils.toJson(recording) + "\n错误原因："
                        + ExceptionUtils.getErrorInfo(e));
            }

            @Override
            public void onProgress(int progress) {
            }
        };
        XLog.d(TAG + "----手动上传话单--->" + GsonUtils.toJson(recording));
        UploadService.Companion.uploadRecordingFile(recording, uploadFileObserve, object);
    }

    public interface OnUploadListener {
        void onSuccess(UploadResponse uploadResponse);

        void onError(Throwable e);
    }


    /**
     * 手动上传话单记录
     *
     * @param recording
     */
    public static void uploadRecording(SysRecording recording, OnUploadListener listener) {
        recording.setUploadStatus(UploadStatus.WAIT_UPLOAD.getStatus());
        recording.setRetryCount(recording.getRetryCount() + 1);
        String fileName = recording.getFileName();
        String object = "";
        if (StringUtils.isNotEmpty(fileName)) {
            object = AliYunUploadManager.generateObjectName(recording.getFileName());
            AliYunManager aliYunManager = AliYunManager.getInstanceAndInit();
            boolean fileExist = aliYunManager.checkFileExist(object);
            XLog.d(TAG + "---文件已经存在于OSS中---->" + fileExist + "-------->" + recording.getFileName());
            if (!fileExist) {
                AliYunUploadManager.uploadFile(object, recording.getAbsolutePath(), new AliYunUploadManager.OnUploadProgressListener() {
                    @Override
                    public void onProgress(long currentSize, long totalSize) {

                    }

                    @Override
                    public void onSuccess() {
                        //文件上传成功
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            } else {
                //文件已经存在OSS中
            }
        }
        XLog.d(TAG + "----手动上传话单--->" + GsonUtils.toJson(recording));

        UploadFileObserve<UploadResponse> uploadFileObserve = new UploadFileObserve<UploadResponse>() {
            @Override
            public void onUploadSuccess(UploadResponse responseBody) {
                int status;
                if (StringUtils.equals("1", responseBody.getType())) {//不是我的客户，或者是回电不记录 或者是 话单已上传 或者是 小号话单跳过
//                    status = UploadStatus.UPLOAD_FAILED.getStatus();
//                    if (StringUtils.isNotEmpty(responseBody.getMsg()) && responseBody.getMsg().contains("话单已上传")) {
                    status = UploadStatus.UPLOAD_IGNORE.getStatus();
                    //  }
                } else {
                    status = UploadStatus.UPLOAD_SUCCEED.getStatus();
                }
                recording.setUploadStatus(status);
                recording.setUploadResult(GsonUtils.toJson(responseBody));
                SysRecordingRepo.updateRecording(recording, new IObserver<Boolean>() {
                    @Override
                    public void onNext(@NotNull @NonNull Boolean aBoolean) {
                        SimAppCache.removeTaskFromQueue(recording);
                    }
                });
                XLog.d(TAG + "---onUploadSuccess：record----\n");
                XLog.json(GsonUtils.toJson(recording) + "\n");
                XLog.json(GsonUtils.toJson(responseBody) + "\n");


                if (listener != null) {
                    listener.onSuccess(responseBody);
                }
            }

            @Override
            public void onUploadFail(Throwable e) {
                recording.setLastRetryTime(System.currentTimeMillis());
                recording.setUploadStatus(UploadStatus.UPLOAD_FAILED.getStatus());
                recording.setUploadResult(e.getMessage());
                SysRecordingRepo.updateRecording(recording, new IObserver<Boolean>() {
                    @Override
                    public void onNext(@NotNull @NonNull Boolean aBoolean) {
                        SimAppCache.removeTaskFromQueue(recording);
                    }
                });

                XLog.e(TAG + "---onUploadFail：record----\n错误原因："
                        + ExceptionUtils.getErrorInfo(e));
                XLog.json(GsonUtils.toJson(recording) + "\n");

                if (listener != null) {
                    listener.onError(e);
                }
            }

            @Override
            public void onProgress(int progress) {
            }
        };

        UploadService.Companion.uploadRecordingFile(recording, uploadFileObserve, object);
    }
}
