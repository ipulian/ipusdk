package com.ipusoft.http;

import android.util.Log;

import com.ipusoft.context.AppContext;
import com.ipusoft.context.OnSDKLoginListener;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.IAuthCode;
import com.ipusoft.context.bean.IToken;
import com.ipusoft.context.constant.Constant;
import com.ipusoft.context.constant.HttpStatus;
import com.ipusoft.context.utils.GsonUtils;
import com.ipusoft.http.module.SDKService;

import java.util.HashMap;

import io.reactivex.rxjava3.annotations.NonNull;

/**
 * author : GWFan
 * time   : 5/17/21 2:43 PM
 * desc   :
 */

public class AuthHttp {
    private static final String TAG = "AuthHttp";

    /**
     * 验证身份信息
     *
     * @param auth
     */
    public static void checkIdentity(String auth) {
        checkIdentity(auth, status -> {
        });
    }


    public static void checkIdentity(String auth, @androidx.annotation.NonNull OnSDKLoginListener loginListener) {
        SDKService.Companion.getAuthCode(auth, new IObserver<IAuthCode>() {
            @Override
            public void onNext(@NonNull IAuthCode result) {
                String status = result.getStatus();
                if (HttpStatus.SUCCESS.equals(status)) {
                    String authCode = result.getAuthCode();
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("authCode", authCode);
                    Log.d(TAG, "onNext: --------<" + GsonUtils.toJson(params));
                    SDKService.Companion.getAuthCodeInfo(params, new IObserver<IToken>() {
                        @Override
                        public void onNext(@NonNull IToken iToken) {
                            String status1 = iToken.getStatus();
                            if (HttpStatus.SUCCESS.equals(status1)) {
                                AppContext.setToken(iToken.getToken());
                                loginListener.onLoginResult(OnSDKLoginListener.LoginStatus.SUCCESS);
                            } else {
                                Log.d(Constant.TAG, "AuthHttp->onNext1: " + iToken.getMsg());
                                loginListener.onLoginResult(OnSDKLoginListener.LoginStatus.FAILED);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d(Constant.TAG, "AuthHttp->onNext2: " + e.toString());
                            loginListener.onLoginResult(OnSDKLoginListener.LoginStatus.FAILED);
                        }
                    });
                } else {
                    Log.d(Constant.TAG, "AuthHttp->onNext3: " + result.getMsg());
                    loginListener.onLoginResult(OnSDKLoginListener.LoginStatus.FAILED);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(Constant.TAG, "AuthHttp->onNext4: " + e.toString());
                loginListener.onLoginResult(OnSDKLoginListener.LoginStatus.FAILED);
            }
        });
    }
}
