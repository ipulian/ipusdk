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

import com.ipusoft.context.cache.AppCacheContext;
import com.ipusoft.context.constant.PhoneState;
import com.ipusoft.context.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;


public class IPhoneStateListener extends android.telephony.PhoneStateListener {
    private static final String TAG = "MyPhoneStateListener";
    private static volatile IPhoneStateListener instance;
    private boolean wasAppInOffHook = false;
    private boolean wasAppInRinging = false;
    private TelephonyManager telephonyManager;
    private List<OnPhoneStateChangedListener> listeners;

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
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
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
            if (ArrayUtils.isNotEmpty(listeners)) {
                PhoneState phoneState = PhoneState.NULL;
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        if (wasAppInRinging || wasAppInOffHook) {
                            Log.d(TAG, "onCallStateChanged: DISCONNECTED");
                            phoneState = PhoneState.DISCONNECTED;
                        }
                        wasAppInRinging = false;
                        wasAppInOffHook = false;
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        if (wasAppInOffHook || wasAppInRinging) {
                            Log.d(TAG, "onCallStateChanged: CONNECTED");
                            phoneState = PhoneState.CONNECTED;
                        } else {
                            Log.d(TAG, "onCallStateChanged: DIALING");
                            phoneState = PhoneState.DIALING;
                        }
                        wasAppInOffHook = true;
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        wasAppInRinging = true;
                        Log.d(TAG, "onCallStateChanged: INCOMING");
                        phoneState = PhoneState.INCOMING;
                        break;
                    default:
                        break;
                }
                dispatcherPhoneState(phoneState);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分发电话事件
     *
     * @param phoneState
     */
    private void dispatcherPhoneState(PhoneState phoneState) {
        AppCacheContext.setPhoneState(phoneState);
        switch (phoneState) {
            case DIALING:
                for (OnPhoneStateChangedListener listener : listeners) {
                    listener.onDialingListener();
                }
                break;
            case INCOMING:
                for (OnPhoneStateChangedListener listener : listeners) {
                    listener.onInComingListener();
                }
                break;
            case CONNECTED:
                for (OnPhoneStateChangedListener listener : listeners) {
                    listener.onConnectedListener();
                }
                break;
            case DISCONNECTED:
                for (OnPhoneStateChangedListener listener : listeners) {
                    listener.onDisConnectedListener();
                }
                break;
            default:
                break;
        }
    }
}

