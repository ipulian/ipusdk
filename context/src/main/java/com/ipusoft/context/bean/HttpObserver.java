package com.ipusoft.context.bean;


import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.base.HttpResponse;
import com.ipusoft.context.component.ToastUtils;
import com.ipusoft.context.constant.HttpStatus;
import com.ipusoft.context.manager.SessionManager;
import com.ipusoft.logger.XLogger;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * author : GWFan
 * time   : 12/15/20 5:50 PM
 * desc   : Http请求的观察者
 */

public abstract class HttpObserver<T extends HttpResponse> extends IObserver<T> {
    private static final String TAG = "HttpObserver";

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        super.onSubscribe(d);
    }

    @Override
    public void onNext(@NotNull T response) {
        ToastUtils.dismiss();
        String httpStatus = response.getHttpStatus();
        if (StringUtils.equals(HttpStatus.SUCCESS, httpStatus)) {
            response.setMsg("");
        } else if (StringUtils.equals(HttpStatus.EXPIRED, httpStatus)) {
            SessionManager.sessionExpired();
        } else if ("102".equals(httpStatus)) {
        } else {
            ToastUtils.showMessage(response.getMsg());
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        ToastUtils.dismiss();
        ToastUtils.showMessage("请求出错，请稍后重试");
        XLogger.e("HttpObserver->onError：" + ExceptionUtils.getErrorInfo(e));
    }
}
