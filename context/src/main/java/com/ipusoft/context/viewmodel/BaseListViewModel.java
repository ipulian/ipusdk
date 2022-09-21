package com.ipusoft.context.viewmodel;

import android.content.Intent;
import android.util.Pair;

import androidx.annotation.Keep;
import androidx.lifecycle.MutableLiveData;

import com.ipusoft.http.RequestMap;

import java.util.List;

/**
 * author : GWFan
 * time   : 9/8/21 4:29 PM
 * desc   :
 */

public abstract class BaseListViewModel<T> extends BaseViewModel {
    private static final String TAG = "BaseListViewModel";

    protected RequestMap requestMap;

    public MutableLiveData<Pair<List<T>, Integer>> listDataLiveData;

    public MutableLiveData<Object> refreshListLiveData;

    @Keep
    public BaseListViewModel() {
        super();
        listDataLiveData = new MutableLiveData<>();
        refreshListLiveData = new MutableLiveData<>();
    }

    @Keep
    public BaseListViewModel(Intent intent) {
        super(intent);
        listDataLiveData = new MutableLiveData<>();
        refreshListLiveData = new MutableLiveData<>();
    }

    public void setRequestMap(RequestMap requestMap) {
        this.requestMap = requestMap;
    }

    public abstract void queryListData(RequestMap requestMap);

    public void refreshListData() {
        refreshListLiveData.postValue(null);
    }
}
