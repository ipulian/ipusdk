package com.ipusoft.oss;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.ipusoft.logger.XLogger;
import com.ipusoft.utils.FileUtilsKt;
import com.ipusoft.utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * author : GWFan
 * time   : 8/3/21 5:41 PM
 * desc   :
 */

public class AliYunDownloadManager extends AliYunManager {

    /**
     * 文件下载
     *
     * @param bucketName
     * @param object
     * @param filepath
     */
    public static void downLoadFile(String bucketName, String object, String filepath, OnProgressListener progressListener) {

        /**
         * 构造下载文件的请求
         */
        GetObjectRequest get = new GetObjectRequest(bucketName, object);

        /**
         * 开启CRC校验文件完整性
         */
        get.setCRC64(OSSRequest.CRC64Config.YES);

        /**
         * 设置下载进度回调
         */
        get.setProgressListener((request, currentSize, totalSize) -> {
            //  Log.d("oss----->", currentsize + "  ----  total_size: " + totalSize);
            //currentSize = currentsize;
        });

        /**
         * 下载文件
         */
        mOSS.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                InputStream inputStream = result.getObjectContent();
                ObjectMetadata metadata = result.getMetadata();
                String eTag = metadata.getETag();
                long resultLength = result.getContentLength();
                byte[] buffer = new byte[2048];
                int len;
                FileOutputStream outputStream = null;
                File cacheFile = new File(filepath);
                if (!cacheFile.exists()) {
                    if (cacheFile.getParentFile() != null) {
                        boolean mkdirs = cacheFile.getParentFile().mkdirs();
                        if (mkdirs) {
                            try {
                                boolean newFile = cacheFile.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                long readSize = 0L;
                try {
                    outputStream = new FileOutputStream(cacheFile, false);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                double lastProgressValue = 0;
                try {
                    while ((len = inputStream.read(buffer)) != -1) {
                        if (outputStream != null) {
                            outputStream.write(buffer, 0, len);
                            readSize += len;
                            String str_currentSize = Long.toString(readSize);
                            String str_totalSize = Long.toString(resultLength);
                            double progress = Math.round((Double.parseDouble(str_currentSize) * 100) / Double.parseDouble(str_totalSize));
                            if (progress % 5 == 0) {
                                if ((progress != lastProgressValue) || (Double.valueOf(str_totalSize).equals(Double.valueOf(str_currentSize)))) {
                                    lastProgressValue = progress;
                                    progressListener.onProgress(DownloadStatus.DOWNLOADING, str_currentSize);
                                }
                            }
                        }

                    }
                    if (outputStream != null) {
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    /**
                     * 根据文件的MD5和文件大小校验文件完整性
                     */
                    String fileMD5 = FileUtilsKt.getFileMD5ToString(new File(filepath));
                    if (StringUtils.isNotEmpty(eTag) && StringUtils.isNotEmpty(fileMD5)) {
                        if (eTag.equals(fileMD5) || FileUtilsKt.getFileLength(new File(filepath)) == resultLength) {
                            progressListener.onProgress(DownloadStatus.DOWNLOADED, filepath);
                        }
                    }
                }
            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientException, ServiceException serviceException) {
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
}
