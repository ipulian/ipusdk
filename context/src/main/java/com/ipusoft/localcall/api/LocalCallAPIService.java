package com.ipusoft.localcall.api;

import com.ipusoft.localcall.bean.UploadResponse;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * author : GWFan
 * time   : 7/10/21 5:39 PM
 * desc   :
 */

public interface LocalCallAPIService {
    /**
     * 录音文件上传
     *
     * @param body
     * @return
     */
    @POST("/app/recorder/uploadCall")
    Observable<UploadResponse> uploadFile(@Body MultipartBody body);
}
