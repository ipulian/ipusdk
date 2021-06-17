package com.ipusoft.context;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * author : GWFan
 * time   : 6/11/21 3:08 PM
 * desc   :
 */

public class ILifecycleObserver implements LifecycleObserver {
    private static final String TAG = "ILifecycleObserver";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onICreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onIPause() {
        Log.d(TAG, "onIPause: ------->");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onIDestroy() {
    }
}
