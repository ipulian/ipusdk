package com.ipusoft.context;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/**
 * author : GWFan
 * time   : 5/10/21 2:09 PM
 * desc   : 实现观察者模式的Service基类
 */

public abstract class BaseLifeCycleService extends Service implements LifecycleOwner {

    private static final String TAG = "BaseLifeCycleService";

    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public void onCreate() {
        super.onCreate();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        onICreate();
        bindLiveData();
    }

    protected abstract void onICreate();

    protected abstract void bindLiveData();

    protected abstract void onIStartCommand(Intent intent, int flags, int startId);

    protected abstract void onIDestroy();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        onIStartCommand(intent, flags, startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        onIDestroy();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    protected void startForeground(String channelName) {
//        String CHANNEL_ONE_ID = "CHANNEL_ONE_ID";
//        String CHANNEL_ONE_NAME = "CHANNEL_ONE_ID";
        NotificationChannel notificationChannel;
        //进行8.0的判断
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelName,
                    channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(notificationChannel);
            }
            //  Intent intent = new Intent(this, MainActivity.class);
            // PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            Notification notification = new Notification.Builder(this, channelName).setChannelId(channelName)
                    //       .setTicker("Nature")
                    //     .setSmallIcon(R.mipmap.ic_launcher)
                    //  .setContentTitle("这是一个测试标题")
                    //     .setContentIntent(pendingIntent)
                    //   .setContentText("这是一个测试内容")
                    .build();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            startForeground(1, notification);
        }
    }
}
