package com.ipusoft.context.bean;


import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.component.ToastUtils;
import com.ipusoft.logger.XLogger;
import com.ipusoft.utils.ExceptionUtils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.annotations.NonNull;

/**
 * author : GWFan
 * time   : 12/15/20 5:50 PM
 * desc   : Http请求的观察者
 */

public abstract class IHttpObserver<T> extends IObserver<T> {
    private static final String TAG = "IHttpObserver";

    @Override
    public abstract void onNext(@NotNull @NonNull T d);

    @Override
    public void onError(@NonNull Throwable e) {
        ToastUtils.dismiss();
        XLogger.d("IHttpObserver->onError：" + ExceptionUtils.getErrorInfo(e));
    }
}
