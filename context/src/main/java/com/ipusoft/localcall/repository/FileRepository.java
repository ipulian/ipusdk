package com.ipusoft.localcall.repository;

import com.ipusoft.utils.FileUtilsKt;

import java.io.File;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * author : GWFan
 * time   : 4/29/21 10:50 AM
 * desc   :
 */

public class FileRepository {

    public static void copyFileAsync(Map<File, File> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            for (Map.Entry<File, File> entry : map.entrySet()) {
                FileUtilsKt.copy(entry.getKey(), entry.getValue());
            }
        }).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
