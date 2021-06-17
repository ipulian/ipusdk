package com.ipusoft.http;

import com.ipusoft.context.LiveDataBus;
import com.ipusoft.context.base.IObserver;
import com.ipusoft.context.bean.SeatInfo;
import com.ipusoft.context.bean.base.BaseHttpResponse;
import com.ipusoft.context.constant.CallTypeConfig;
import com.ipusoft.context.constant.HttpStatus;
import com.ipusoft.context.constant.LiveDataConstant;
import com.ipusoft.http.module.SDKService;
import com.ipusoft.context.utils.ArrayUtils;
import com.ipusoft.context.utils.StringUtils;
import com.ipusoft.mmkv.datastore.AppDataStore;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

/**
 * author : GWFan
 * time   : 1/11/21 5:10 PM
 * desc   :
 */

public class QuerySeatInfoHttp {
    private static final String TAG = "QuerySeatInfoHttp";

    public interface OnQuerySeatInfoListener {
        void onQuerySeatInfo(SeatInfo seatInfo, String localCallType);
    }

    /**
     * 查询坐席信息
     */
    public static void querySeatInfo(OnQuerySeatInfoListener listener) {
        SDKService.Companion.querySeatInfo(RequestMap.getRequestMap(), new IObserver<SeatInfo>() {
            @Override
            public void onNext(@NonNull SeatInfo seatInfo) {
                //  SipDataStore.setSeatInfo(seatInfo);
                String status = seatInfo.getStatus();
                if (StringUtils.equals(HttpStatus.SUCCESS, status)) {
                    String callType = seatInfo.getCallType();
                    String localCallType = AppDataStore.getLocalCallType();
                    if (StringUtils.isNotEmpty(callType)) {
                        List<String> list = ArrayUtils.createList(callType.split(","));
                        if (list.size() == 1) {
                            localCallType = callType;
                        } else if (list.size() == 2) {
                            if (list.contains(CallTypeConfig.SIM.getType())
                                    && list.contains(CallTypeConfig.SIP.getType())) {
                                if (StringUtils.isEmpty(localCallType)) {
                                    //SipPhoneManager.registerSip();
                                    localCallType = CallTypeConfig.SIP.getType();
                                } else {
                                    if (StringUtils.equals(localCallType, CallTypeConfig.X.getType())) {
                                        // SipPhoneManager.registerSip();
                                        localCallType = CallTypeConfig.SIP.getType();
                                    } else if (StringUtils.equals(localCallType, CallTypeConfig.SIP.getType())) {
                                        //SipPhoneManager.registerSip();
                                        localCallType = CallTypeConfig.SIP.getType();
                                    } else if (StringUtils.equals(localCallType, CallTypeConfig.SIM.getType())) {
                                        localCallType = CallTypeConfig.SIM.getType();
                                    } else {
                                        // SipPhoneManager.registerSip();
                                        localCallType = CallTypeConfig.SIP.getType();
                                    }
                                }
                            } else if (list.contains(CallTypeConfig.SIM.getType())
                                    && list.contains(CallTypeConfig.X.getType())) {
                                if (StringUtils.isEmpty(localCallType)) {
                                    localCallType = CallTypeConfig.X.getType();
                                } else {
                                    if (StringUtils.equals(localCallType, CallTypeConfig.X.getType())) {
                                        localCallType = CallTypeConfig.X.getType();
                                    } else if (StringUtils.equals(localCallType, CallTypeConfig.SIP.getType())) {
                                        localCallType = CallTypeConfig.X.getType();
                                    } else if (StringUtils.equals(localCallType, CallTypeConfig.SIM.getType())) {
                                        localCallType = CallTypeConfig.SIM.getType();
                                    } else {
                                        localCallType = CallTypeConfig.X.getType();
                                    }
                                }
                            } else if (list.contains(CallTypeConfig.SIP.getType())
                                    && list.contains(CallTypeConfig.X.getType())) {
                                if (StringUtils.isEmpty(localCallType)) {
                                    localCallType = CallTypeConfig.X.getType();
                                } else {
                                    if (StringUtils.equals(localCallType, CallTypeConfig.X.getType())) {
                                        localCallType = CallTypeConfig.X.getType();
                                    } else if (StringUtils.equals(localCallType, CallTypeConfig.SIP.getType())) {
                                        //SipPhoneManager.registerSip();
                                        localCallType = CallTypeConfig.SIP.getType();
                                    } else if (StringUtils.equals(localCallType, CallTypeConfig.SIM.getType())) {
                                        localCallType = CallTypeConfig.X.getType();
                                    } else {
                                        localCallType = CallTypeConfig.X.getType();
                                    }
                                }
                            }
                        } else if (list.size() == 3) {
                            if (StringUtils.isEmpty(localCallType)) {
                                localCallType = CallTypeConfig.X.getType();
                            } else {
                                if (StringUtils.equals(localCallType, CallTypeConfig.X.getType())) {
                                    localCallType = CallTypeConfig.X.getType();
                                } else if (StringUtils.equals(localCallType, CallTypeConfig.SIP.getType())) {
                                    // SipPhoneManager.registerSip();
                                    localCallType = CallTypeConfig.SIP.getType();
                                } else if (StringUtils.equals(localCallType, CallTypeConfig.SIM.getType())) {
                                    localCallType = CallTypeConfig.SIM.getType();
                                } else {
                                    localCallType = CallTypeConfig.X.getType();
                                }
                            }
                        }

                        if (listener != null) {
                            listener.onQuerySeatInfo(seatInfo, localCallType);
                        }

                        AppDataStore.setLocalCallType(localCallType);

                        LiveDataBus.get().with(LiveDataConstant.REFRESH_CALL_TYPE_CONFIG, String.class)
                                .postValue(localCallType);

                        updateCallType(localCallType);
                    }
                }
            }
        });
    }

    /**
     * 向服务端同步外呼方式
     *
     * @param callType
     */
    private static void updateCallType(String callType) {
        RequestMap requestMap = RequestMap.getRequestMap();
        requestMap.put("callType", callType);
        SDKService.Companion.updateCallType(requestMap, new IObserver<BaseHttpResponse>() {
            @Override
            public void onNext(@NonNull BaseHttpResponse baseHttpResponse) {

            }
        });
    }
}
