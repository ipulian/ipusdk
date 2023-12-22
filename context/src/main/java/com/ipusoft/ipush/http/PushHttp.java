package com.ipusoft.ipush.http;

import android.util.Log;

import androidx.annotation.NonNull;

import com.elvishew.xlog.XLog;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.bean.HttpObserver;
import com.ipusoft.context.bean.base.HttpResponse;
import com.ipusoft.context.constant.HttpStatus;
import com.ipusoft.http.RequestMap;
import com.ipusoft.ipush.IPushLifecycle;
import com.ipusoft.ipush.PushEventHandler;
import com.ipusoft.ipush.bean.PushMessage;
import com.ipusoft.ipush.bean.WebLinkPushBody;
import com.ipusoft.ipush.datastore.PushDataRepo;
import com.ipusoft.ipush.module.IPushService;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;

import org.jetbrains.annotations.NotNull;


/**
 * author : GWFan
 * time   : 5/14/21 10:57 PM
 * desc   :
 */

public class PushHttp {
    private static final String TAG = "PushHttp";
    private static boolean lock = false;

    /**
     * Web联动的App状态回调
     *
     * @param callId
     */
    public static void pushCallBack(String callId) {
        RequestMap requestMap = RequestMap.getRequestMap();
        requestMap.put("callId", callId);
        IPushService.Companion.callBack(requestMap, new HttpObserver<HttpResponse>() {
            @Override
            public void onNext(@NotNull HttpResponse httpResponse) {

            }
        });
    }

    /**
     * 更新JPushRegId
     */
    public static void updateRegId() {
        String pushRegId = PushDataRepo.getPushRegId();
        if (StringUtils.isEmpty(pushRegId)) {
            if (!lock) {
                lock = true;
                RequestMap requestMap = RequestMap.getRequestMap();
                requestMap.put("device", "app");
                requestMap.put("userId", AppContext.getUid());
                String registrationID = IPushLifecycle.getRegistrationID();
                XLog.d("PushHttp->updateRegId，参数：" + GsonUtils.toJson(requestMap));
                if (StringUtils.isNotEmpty(registrationID)) {
                    requestMap.put("regId", registrationID);
                    IPushService.Companion.updateRegId(requestMap, new HttpObserver<HttpResponse>() {
                        @Override
                        public void onNext(@NotNull @NonNull HttpResponse httpResponse) {
                            lock = false;
                            if (StringUtils.equals(HttpStatus.SUCCESS, httpResponse.getHttpStatus())) {
                                PushDataRepo.setPushRegId(registrationID);
                            } else {
                                XLog.d("PushHttp->updateRegId，错误：" + httpResponse.getHttpStatus());
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * 轮询推送消息
     */
    public static void getPushMsg() {
        String token = AppContext.getToken();
        if (StringUtils.isNotEmpty(token)) {
            Log.d(TAG, "onIStartCommand: ----------------5");
            IPushService.Companion.getPushMsg(RequestMap.getRequestMap(), new HttpObserver<PushMessage>() {
                @Override
                public void onNext(@NonNull PushMessage response) {
                    try {
                        String httpStatus = response.getHttpStatus();
                        if (StringUtils.equals(HttpStatus.SUCCESS, httpStatus)) {
                            String pushMessage = response.getCallMessage();
                            XLog.d(TAG + "---:pushMessage: -------->" + pushMessage);
                            if (StringUtils.isNotEmpty(pushMessage)) {
                                new PushEventHandler().distributeEvent(GsonUtils.fromJson(pushMessage, WebLinkPushBody.class));
                            } else {
                                // XLog.d(TAG + "--->（pushMessage == null）");
                            }
                        } else if (StringUtils.equals(HttpStatus.EXPIRED, httpStatus)) {
//                            IpuSoftSDK.unInitIModule();
//                            AccountMMKV.clearAll();
//
//                            IDialog.getInstance()
//                                    .setMsg("登录信息已过期，请重新登录")
//                                    .setShowCancelBtn(false)
//                                    .setOnConfirmClickListener(() -> {
//                                        List<Activity> activityList = IActivityLifecycle.getActivityStack();
//                                        if (ArrayUtils.isNotEmpty(activityList)) {
//                                            for (Activity activity : activityList) {
//                                                activity.finish();
//                                            }
//                                        }
//                                        try {
//                                            Class<?> clazz = Class.forName("com.ipusoft.lianlian.np.view.activity.login.LoginActivity");
//                                            AppContext.getActivityContext().finish();
//                                            Intent intent = new Intent(AppContext.getActivityContext(), clazz);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                            AppContext.getActivityContext().startActivity(intent);
//                                        } catch (ClassNotFoundException e) {
//                                            e.printStackTrace();
//                                            Log.d(TAG, "sessionExpired: --------->" + ExceptionUtils.getErrorInfo(e));
//                                        }
//                                    })
//                                    .show();
                        } else {
                            XLog.e(TAG + "--->（httpStatus ！= SUCCESS----" + GsonUtils.toJson(response));
                        }
                    } catch (Exception e) {
                        XLog.e(TAG + "---1>" + ExceptionUtils.getErrorInfo(e));
                    }
                }

                @Override
                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    //  XLog.e(TAG + "---2>" + ExceptionUtils.getErrorInfo(e));
                }
            });
        } else {
            XLog.d("--------token为空----------");
        }
    }
}
