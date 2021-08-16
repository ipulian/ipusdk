package com.ipusoft.localcall;

import com.ipusoft.context.base.IObserver;

import org.jetbrains.annotations.NotNull;

/**
 * author : GWFan
 * time   : 4/9/21 11:40 AM
 * desc   : 文件上传的Observe
 */

public abstract class UploadFileObserve<T> extends IObserver<T> {
    private static final String TAG = "UploadFileObserve";

    @Override
    public void onNext(@NotNull T t) {
        onUploadSuccess(t);
    }

    @Override
    public void onError(@NotNull Throwable e) {
        onUploadFail(e);
    }

    @Override
    public void onComplete() {

    }

    // 上传成功的回调
    public abstract void onUploadSuccess(T t);

    // 上传失败回调
    public abstract void onUploadFail(Throwable e);

    // 上传进度回调
    public abstract void onProgress(int progress);

    // 监听进度的改变
    public void onProgressChange(long bytesWritten, long contentLength) {
        onProgress((int) (bytesWritten * 100 / contentLength));
    }
}
