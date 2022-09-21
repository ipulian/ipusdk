package com.ipusoft.http.api;

import com.ipusoft.context.bean.IAuthCode;
import com.ipusoft.context.bean.IToken;
import com.ipusoft.context.bean.SeatInfo;
import com.ipusoft.context.bean.base.HttpResponse;
import com.ipusoft.http.HttpConstant;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    /**
     * 返回坐席呼叫类型
     *
     * @param requestBody
     * @return
     */
    @Headers({HttpConstant.CONTENT_TYPE})
    @POST("/call/getSeatInfo")
    Observable<SeatInfo> querySeatInfo(@Body RequestBody requestBody);

    /**
     * 切换呼叫方式
     *
     * @param requestBody
     * @return
     */
    @Headers({HttpConstant.CONTENT_TYPE})
    @POST("/app/user/status")
    Observable<HttpResponse> updateCallType(@Body RequestBody requestBody);
}
