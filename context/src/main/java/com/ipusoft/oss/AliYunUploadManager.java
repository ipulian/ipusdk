package com.ipusoft.oss;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.elvishew.xlog.XLog;
import com.ipusoft.utils.GsonUtils;

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
}
