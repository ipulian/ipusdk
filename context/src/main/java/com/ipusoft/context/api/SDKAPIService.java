package com.ipusoft.context.api;

import com.ipusoft.context.bean.IAuthCode;
import com.ipusoft.context.bean.IToken;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * author : GWFan
 * time   : 5/17/21 2:43 PM
 * desc   :
 */

public interface SDKAPIService {

    /**
     * 获取授权码
     *
     * @param auth
     * @return
     */
    @GET("/getAuthCode/{auth}")
    Observable<IAuthCode> getAuthCode(@Path("auth") String auth);


    /**
     * 获取Token
     */
    @POST("/getAuthCodeInfo")
    @FormUrlEncoded
    Observable<IToken> getAuthCodeInfo(@FieldMap Map<String, Object> params);
}
