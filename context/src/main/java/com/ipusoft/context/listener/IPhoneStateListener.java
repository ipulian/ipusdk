package com.ipusoft.context.listener;

/**
 * author : GWFan
 * time   : 2020/5/27 15:19
 * desc   : 通话状态的listener
 */

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;


public class IPhoneStateListener extends android.telephony.PhoneStateListener {
    private static final String TAG = "MyPhoneStateListener";
    private static volatile IPhoneStateListener instance;
    private boolean wasAppInOffHook = false;
    private boolean wasAppInRinging = false;
    private TelephonyManager telephonyManager;
    private OnPhoneStateChangedListener listener;

    public static IPhoneStateListener getInstance() {
        if (instance == null) {
            synchronized (IPhoneStateListener.class) {
                if (instance == null) {
                    instance = new IPhoneStateListener();
                }
            }
        }
        return instance;
    }

    /**
     * 开启通话状态监听
     */
    public void registerPhoneListener(Application context, OnPhoneStateChangedListener listener) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(this, IPhoneStateListener.LISTEN_CALL_STATE);
        this.listener = listener;
    }

    /**
     * 关闭通话状态监听
     */
    public void stopListener() {
        if (telephonyManager != null) {
            telephonyManager.listen(this, IPhoneStateListener.LISTEN_NONE);
            telephonyManager = null;
        }
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        try {
            if (listener != null) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        if (wasAppInRinging || wasAppInOffHook) {
                            Log.d(TAG, "onCallStateChanged: DISCONNECTED");
                            listener.onDisConnectedListener();
                        }
                        wasAppInRinging = false;
                        wasAppInOffHook = false;
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        if (wasAppInOffHook || wasAppInRinging) {
                            Log.d(TAG, "onCallStateChanged: CONNECTED");
                            listener.onConnectedListener();
                        } else {
                            Log.d(TAG, "onCallStateChanged: DIALING");
                            listener.onDialingListener();
                        }
                        wasAppInOffHook = true;
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        wasAppInRinging = true;
                        Log.d(TAG, "onCallStateChanged: INCOMING");
                        listener.onInComingListener();
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

