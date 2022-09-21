package com.ipusoft.localcall.upload;

import com.ipusoft.http.RequestMap;
import com.ipusoft.localcall.UploadFileObserve;
import com.ipusoft.localcall.bean.UploadResponse;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.FileUtilsKt;
import com.ipusoft.utils.MapUtils;
import com.ipusoft.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;


/**
 * author : GWFan
 * time   : 4/9/21 12:04 PM
 * desc   :
 */

public class BuyPersonalLessonMultipartBuilder {
    private static final String TAG = "BuyPersonalLessonMultip";

    /**
     * 单文件上传
     *
     * @param file
     * @return fileUploadObserver
     */
    public static MultipartBody file2MultipartBody(File file, RequestMap requestMap, UploadFileObserve<UploadResponse> observe) {
        ArrayList<File> list = new ArrayList<>();
        if (file != null) {
            list.add(file);
        }
        return files2MultipartBody(list, requestMap, observe);
    }

    /**
     * 多文件上传
     *
     * @param files   文件列表
     * @param observe 文件上传回调
     */
    public static MultipartBody files2MultipartBody(List<File> files, RequestMap requestMap, UploadFileObserve<UploadResponse> observe) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        UploadFileRequestBody uploadFileRequestBody;
        if (ArrayUtils.isNotEmpty(files)) {
            for (File file : files) {
                if (file != null && StringUtils.isNotEmpty(file.getAbsolutePath())) {
                    uploadFileRequestBody = new UploadFileRequestBody(file.getAbsolutePath(), observe);
                    String fileName = System.currentTimeMillis() + "." + FileUtilsKt.getFileExtension(file);
                    builder.addFormDataPart("files", fileName, uploadFileRequestBody);
                }
            }
        }
        if (MapUtils.isNotEmpty(requestMap)) {
            for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
                builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }
}