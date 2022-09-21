package com.ipusoft.context.base;

import androidx.annotation.Keep;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * author : GWFan
 * time   : 5/24/21 6:51 PM
 * desc   : 数据观察者的基类
 */

@Keep
public class IObserver<T> implements Observer<T> {
    private static final String TAG = "IObserver";

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
