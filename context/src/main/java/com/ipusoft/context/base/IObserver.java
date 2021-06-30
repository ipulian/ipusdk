package com.ipusoft.context.base;

import com.ipusoft.context.component.ToastUtils;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * author : GWFan
 * time   : 5/24/21 6:51 PM
 * desc   :
 */

public class IObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        ToastUtils.dismiss();
    }

    @Override
    public void onComplete() {

    }
}
