package com.ipusoft.context;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author : GWFan
 * time   : 5/2/21 4:15 PM
 * desc   :
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化Binding 和 ViewModel
        initViewModel();
        //初始化数据
        initData();
        //初始化UI
        initUI();
        //绑定LiveData
        bindLiveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //页面初始化时的网络请求
        initRequest();
    }

    protected abstract void initViewModel();

    protected void initData() {
    }

    protected abstract void initUI();

    protected void bindLiveData() {
    }

    protected void initRequest() {
    }
}
