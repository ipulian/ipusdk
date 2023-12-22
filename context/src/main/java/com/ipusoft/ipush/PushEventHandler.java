package com.ipusoft.ipush;

import android.util.Log;

import com.ipusoft.context.bean.BindInfo;
import com.ipusoft.context.bean.Clue;
import com.ipusoft.context.bean.Customer;
import com.ipusoft.context.bean.CustomerCallBean;
import com.ipusoft.context.constant.CallTypeConfig;
import com.ipusoft.ipush.bean.PushPhoneEvent2App;
import com.ipusoft.ipush.bean.WebLinkPushBody;
import com.ipusoft.ipush.http.PushHttp;
import com.ipusoft.ipush.listener.WebLinkListener;
import com.elvishew.xlog.XLog;
import com.ipusoft.mmkv.datastore.CommonDataRepo;
import com.ipusoft.utils.ArrayUtils;
import com.ipusoft.utils.GsonUtils;
import com.ipusoft.utils.StringUtils;

import java.util.List;

/**
 * author : GWFan
 * time   : 5/12/21 3:14 PM
 * desc   :
 */

public class PushEventHandler {
    private static final String TAG = "PushEventHandler";
    /**
     * 外呼
     */
    public static final String CALL = "CALL";
    /**
     * 呼入
     */
    public static final String CALL_IN = "CALL_IN";
    /**
     * 发送
     */
    public static final String SMS = "SMS";
    /**
     * 接收
     */
    public static final String SMS_IN = "SMS_IN";

    /**
     * 外呼(SIM_X,SIP_X,SIM,SIP)
     */
    public static final String CALL_APP = "CALL_APP";

    public static final String LOG_NOTIFY = "LOG_NOTIFY";

    public static final String UPLOAD_LOG = "UPLOAD_LOG";

    public static final String APP_VERSION = "APP_VERSION";

    private String xPhone = "",
            xPhoneArea = "",
            phoneArea = "",
            channelName = "",
    //联动(回电的真实号码)
    callPhone = "",
            callId = "";

    private PushPhoneEvent2App pushPhoneEvent2App;

    private WebLinkPushBody.DataBean data;

    private void initCommonData() {
        xPhone = "";
        xPhoneArea = "";
        phoneArea = "";
        channelName = "";
        callPhone = "";
        callId = "";
        pushPhoneEvent2App = new PushPhoneEvent2App();
        data = null;
    }

    public void distributeEvent(WebLinkPushBody webLinkPushBody) {
        WebLinkListener linkListener = IPushLifecycle.getWebLinkListener();
        /*
         * 重置基本信息
         */
        initCommonData();

        Log.d(TAG, "distributeEvent: -------------------->" + GsonUtils.toJson(webLinkPushBody));

        if (webLinkPushBody != null && linkListener != null) {

            String type = webLinkPushBody.getType();
            if (StringUtils.equals(type, UPLOAD_LOG) || StringUtils.equals(type, APP_VERSION)) {
                //NOThing
            } else {
                if (System.currentTimeMillis() - webLinkPushBody.getTime() > 18 * 1000) {
                    Log.d(TAG, "PushEventHandler:超过15s的推送消息，忽略掉");
                    return;
                }
            }
            data = webLinkPushBody.getData();
            if (data == null) {
                return;
            }

            pushPhoneEvent2App.setPushType(type);

            callId = data.getCallId();

            String callType = data.getCallType();
            if (StringUtils.equals(type, LOG_NOTIFY)) {//消息通知

            } else if (StringUtils.equals(type, UPLOAD_LOG)) {
                Log.d(TAG, "distributeEvent: .------------>" + GsonUtils.toJson(data));
                String date = data.getDate();
                Log.d(TAG, "distributeEvent: .------------>" + date);
                pushPhoneEvent2App.setDate(date);
            } else if (StringUtils.equals(type, APP_VERSION)) {
                //"deleted":"0",
                //"channel":"llwh-pre",
                //"ctime":"2023-08-04 17:35:23",
                //"remark":"测试版本",
                //"id":"4802695856914447",
                //"versionName":"2.08.04",
                //"type":"0",
                //"versionCode":"2080401",
                //"url":"ali-oss://oss-cn-beijing/ipufiles/apk_new/llwh-pre/lianwh-2.06.29.apk",
                //"appSize":"40223",
                //"isUpdate":"1",
                //"status":"1"
                pushPhoneEvent2App.setChannel(data.getChannel());
                pushPhoneEvent2App.setRemark(data.getRemark());
                pushPhoneEvent2App.setVersionName(data.getVersionName());
                pushPhoneEvent2App.setVersionCode(data.getVersionCode());
                pushPhoneEvent2App.setUrl(data.getUrl());
                pushPhoneEvent2App.setAppSize(data.getAppSize());
                pushPhoneEvent2App.setIsUpdate(data.getIsUpdate());

            } else {
                if (StringUtils.equals(CALL, type) || StringUtils.equals(CALL_IN, type)
                        || StringUtils.equals(CALL_APP, type)) {//电话
                    /*
                     * 从推送消息中获取客户，线索，陌生联系人的相关信息
                     */
                    queryCallInfoFromPushBody();

                    if (StringUtils.equals(CALL, type) || StringUtils.equals(CALL_APP, type)) {//外呼
                        /*
                         * 外呼的时候向服务端上报状态
                         */
                        PushHttp.pushCallBack(callId);

                        pushPhoneEvent2App.setType(1);
                    } else {
                        pushPhoneEvent2App.setType(2);//呼入
                    }
                } else if (StringUtils.equals(SMS, type)) {//短信
                    pushPhoneEvent2App.setType(3);//发送
                    pushPhoneEvent2App.setContent(data.getContent());
                } else if (StringUtils.equals(SMS_IN, type)) {
                    pushPhoneEvent2App.setType(4);//接收
                }

                if (StringUtils.equals(CallTypeConfig.SIM.getType(), callType)) {
                    pushPhoneEvent2App.setCallType(1);//SIM
                } else if (StringUtils.equals(CallTypeConfig.X.getType(), callType)) {
                    pushPhoneEvent2App.setCallType(2);//X
                } else if (StringUtils.equals(CallTypeConfig.SIP.getType(), callType)) {
                    pushPhoneEvent2App.setCallType(3);//SIP
                } else if (StringUtils.equals(CallTypeConfig.TYC.getType(), callType)) {
                    pushPhoneEvent2App.setCallType(4);//TYC
                } else {
                    callType = CommonDataRepo.getLocalCallType();
                    if (StringUtils.equals(CallTypeConfig.SIM.getType(), callType)) {
                        pushPhoneEvent2App.setCallType(1);//SIM
                    } else if (StringUtils.equals(CallTypeConfig.X.getType(), callType)) {
                        pushPhoneEvent2App.setCallType(2);//X
                    } else if (StringUtils.equals(CallTypeConfig.SIP.getType(), callType)) {
                        pushPhoneEvent2App.setCallType(3);//SIP
                    } else if (StringUtils.equals(CallTypeConfig.TYC.getType(), callType)) {
                        pushPhoneEvent2App.setCallType(4);//TYC
                    }
                }

                BindInfo bindInfo = data.getBindInfo();
                //联系人真实号码
                callPhone = data.getPhone();

                String virtualNumber = "";
                //SIM和SIP模式下bindInfo为null
                if (bindInfo != null) {
                    virtualNumber = bindInfo.getVirtualNumber();
                    xPhone = bindInfo.getXPhone();
                    xPhoneArea = bindInfo.getXPhoneArea();
                    phoneArea = bindInfo.getPhoneArea();
                    channelName = bindInfo.getChannelName();

                    if (StringUtils.equals(CALL_IN, type)) {
                        List<String> caller = bindInfo.getCaller();
                        if (ArrayUtils.isNotEmpty(caller)) {
                            callPhone = caller.get(0);
                        }
                    }
                }

                pushPhoneEvent2App.setPhone(callPhone);
                pushPhoneEvent2App.setXPhone(xPhone);
                pushPhoneEvent2App.setXPhoneArea(xPhoneArea);
                pushPhoneEvent2App.setPhoneArea(phoneArea);
                pushPhoneEvent2App.setVirtualNumber(virtualNumber);
                pushPhoneEvent2App.setCallId(callId);
                pushPhoneEvent2App.setCallTime(data.getCallTime());
                pushPhoneEvent2App.setChannelName(channelName);
                pushPhoneEvent2App.setTaskType(data.getTaskType());
                pushPhoneEvent2App.setTaskId(data.getTaskId());

            }
            XLog.d("distributeEvent：" + GsonUtils.toJson(pushPhoneEvent2App));
            //将推送消息发送给App
            linkListener.onWebLinkListener(pushPhoneEvent2App);
        }
    }

    private void queryCallInfoFromPushBody() {
        if (StringUtils.equals("1", data.getIsClue())) {
            Clue clue = GsonUtils.fromJson(GsonUtils.toJson(data.getClues()), Clue.class);
            if (clue.getSex() != null) {
                clue.setMSex(clue.getSex() == 0 ? "女" : clue.getSex() == 1 ? "男" : "");
            }
            pushPhoneEvent2App.setDataType(2);//线索
            pushPhoneEvent2App.setClue(clue);
        } else {
            CustomerCallBean pushCustomer = data.getCustomers();
            if (pushCustomer != null && pushCustomer.getCustomerId() != null && pushCustomer.getCustomerId() != 0) {
                Log.d(TAG, "queryCallInfoFromPushBody: ");
                Customer customer = getCustomerInfo(pushCustomer);
                pushPhoneEvent2App.setDataType(1);//客户
                pushPhoneEvent2App.setCustomer(customer);
            } else {
                pushPhoneEvent2App.setDataType(3);//陌生
            }
        }
    }

    /**
     * 获取客户信息
     *
     * @param pushCustomer
     * @return
     */
    private Customer getCustomerInfo(CustomerCallBean pushCustomer) {
        Customer customer = null;
        if (pushCustomer != null) {
            customer = GsonUtils.fromJson(GsonUtils.toJson(pushCustomer), Customer.class);
            if (customer.getSex() != null) {
                customer.setMSex(customer.getSex() == 0 ? "女" : customer.getSex() == 1 ? "男" : "");
            }
            customer.setCustomerId(pushCustomer.getCustomerId());
        }
        return customer;
    }
}
