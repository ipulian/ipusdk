package com.ipusoft.oss;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.CannedAccessControlList;
import com.alibaba.sdk.android.oss.model.CreateBucketRequest;
import com.alibaba.sdk.android.oss.model.CreateBucketResult;
import com.alibaba.sdk.android.oss.model.DeleteBucketRequest;
import com.alibaba.sdk.android.oss.model.DeleteBucketResult;
import com.alibaba.sdk.android.oss.model.HeadObjectRequest;
import com.alibaba.sdk.android.oss.model.HeadObjectResult;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.ipusoft.context.AppContext;
import com.ipusoft.logger.XLogger;

/**
 * Created by GWFan on 2018/10/8.
 */

public class AliYunManager {
    private static final String TAG = "AliYunManager";

    protected static OSS mOSS;

    private String objectName;

    /**
     * OSSKey
     */
    public static final String OSS_ACCESS_KEY_ID = "LTAIUDB32w25akBg";

    /**
     * OSSSecret
     */
    public static final String OSS_ACCESS_KEY_SECRET = "Q7uOZz5iVO6uZNNRjjB1GvfewtVWQc";

    /**
     * 指定Bucket所在的数据中心(节点)
     */
    public static final String OSS_END_POINT = "oss-cn-beijing";

    public static final String BUCKET_NAME = "ipufiles";

    private static class AliYunManagerHolder {
        private static final AliYunManager INSTANCE = new AliYunManager();
    }

    public static AliYunManager getInstance() {
        return AliYunManagerHolder.INSTANCE;
    }

    public AliYunManager setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public interface OnProgressListener {
        void onProgress(DownloadStatus DownloadStatus, String progress);
    }

    public interface MetaDataCallback {
        void callback(ObjectMetadata objectMetadata);
    }

    /**
     * 初始化阿里云OSS
     */
    public AliYunManager initAliOSS() {
        if (mOSS == null) {
            OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET);
            mOSS = new OSSClient(AppContext.getAppContext(), "http://" + AliYunManager.OSS_END_POINT + ".aliyuncs.com",
                    credentialProvider);
        }
        return this;
    }


    /**
     * 获取文件元信息
     */
    public void getMetadata(String bucketName, MetaDataCallback metaDataCallback) {
        mOSS.asyncHeadObject(new HeadObjectRequest(bucketName, objectName), new OSSCompletedCallback<HeadObjectRequest, HeadObjectResult>() {
            @Override
            public void onSuccess(HeadObjectRequest request, HeadObjectResult result) {
                metaDataCallback.callback(result.getMetadata());
            }

            @Override
            public void onFailure(HeadObjectRequest request, ClientException clientException, ServiceException serviceException) {
                if (clientException != null) {
                    clientException.printStackTrace();
                    XLogger.d(clientException);
                }
                if (serviceException != null) {
                    XLogger.d(serviceException);
                }
            }
        });
    }

    /**
     * 创建bucket
     */
    public void createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        // 指定Bucket的ACL权限。
        createBucketRequest.setBucketACL(CannedAccessControlList.Private);
        // 指定Bucket所在的数据中心。
        createBucketRequest.setLocationConstraint(OSS_END_POINT);
        // 异步创建存储空间。
        mOSS.asyncCreateBucket(createBucketRequest, new OSSCompletedCallback<CreateBucketRequest, CreateBucketResult>() {
            @Override
            public void onSuccess(CreateBucketRequest request, CreateBucketResult result) {
                XLogger.e("createBucket Success");
            }

            @Override
            public void onFailure(CreateBucketRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常。
                if (clientException != null) {
                    // 本地异常，如网络异常等。
                    clientException.printStackTrace();
                    XLogger.e(clientException);
                }
                if (serviceException != null) {
                    // 服务异常。
                    XLogger.e(serviceException);
                }
            }
        });
    }

    /**
     * 删除bucket
     *
     * @param bucketName bucket名称
     */
    public static void deleteBucket(String bucketName) {
        DeleteBucketRequest deleteBucketRequest = new DeleteBucketRequest(bucketName);
        // 异步删除存储空间。
        mOSS.asyncDeleteBucket(deleteBucketRequest, new OSSCompletedCallback<DeleteBucketRequest, DeleteBucketResult>() {
            @Override
            public void onSuccess(DeleteBucketRequest request, DeleteBucketResult result) {
                XLogger.d("deleteBucket Success");
            }

            @Override
            public void onFailure(DeleteBucketRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常。
                if (clientException != null) {
                    // 本地异常，如网络异常等。
                    clientException.printStackTrace();
                    XLogger.e(clientException);
                }
                if (serviceException != null) {
                    // 服务异常。
                    XLogger.e(serviceException);
                }
            }
        });
    }

    /**
     * 检查文件是否存在
     */
    public boolean checkFileExist(String objectKey) {
        try {
            if (mOSS.doesObjectExist(AliYunManager.BUCKET_NAME, objectKey)) {
                Log.d(TAG, "OSS文件 存在");
                return true;
            } else {
                Log.d(TAG, "OSS文件 不存在");
                return false;
            }
        } catch (ClientException e) {
            // 本地异常如网络异常等
            e.printStackTrace();
        } catch (ServiceException e) {
            // 服务异常
            XLogger.d("ErrorCode->" + e.getErrorCode());
            XLogger.d("RequestId->" + e.getRequestId());
            XLogger.d("HostId->" + e.getHostId());
            XLogger.d("RawMessage->" + e.getRawMessage());
        }
        return false;
    }
}
