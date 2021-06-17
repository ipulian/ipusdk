package com.ipusoft.context;

import android.app.Application;
import android.util.Log;

import com.ipusoft.context.bean.IAuthInfo;
import com.ipusoft.context.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * author : GWFan
 * time   : 6/9/21 9:12 AM
 * desc   :
 */

public abstract class LianLianApp extends IpuSoftSDK {
    private static final String TAG = "LianLianApp";

    public static String SERVICE_AGREEMENT = "";
    public static String PRIVACY_POLICY = "";

    /**
     * 内部认证信息
     *
     * @param mApp
     * @throws RuntimeException
     */

    public static void init(Application mApp, Class<?> clazz) {
        getAppConfig(clazz);
        init(mApp, env);
    }

    /**
     * @param iAuthInfo 更新内部认证信息
     */
    public static void updateIAuthInfo(IAuthInfo iAuthInfo) {
        if (iAuthInfo != null) {
            IAuthInfo oldIAuthInfo = AppContext.getIAuthInfo();
            if (oldIAuthInfo != null && !oldIAuthInfo.equals(iAuthInfo)) {
                unInitIModule();
            }
            AppContext.setIAuthInfo(iAuthInfo);
            querySeatInfo();
        } else {
            throw new RuntimeException("认证信息为空 IAuthInfo == null");
        }
    }

    private static void getAppConfig(Class<?> appConfigClazz) {
        Field[] fields = appConfigClazz.getFields();
        for (Field field : fields) {
            String descriptor = Modifier.toString(field.getModifiers());
            descriptor = descriptor.equals("") ? "" : descriptor + " ";
            try {
                Log.d(TAG, "getAppConfig: 修饰符：" + descriptor);
                Log.d(TAG, "getAppConfig: 变量名：" + field.getName());
                Log.d(TAG, "getAppConfig: 变量值：" + field.get(appConfigClazz.newInstance()));
                String fieldName = field.getName();
                String fieldValue = (String) field.get(appConfigClazz.newInstance());
                if (StringUtils.equals("RUNTIME_ENV", fieldName)) {
                    env = fieldValue;
                } else if (StringUtils.equals("BASE_URL", fieldName)) {
                    BASE_URL = fieldValue;
                } else if (StringUtils.equals("GATE_WAY_URL", fieldName)) {
                    GATE_WAY_URL = fieldValue;
                } else if (StringUtils.equals("SIP_URL", fieldName)) {
                    SIP_URL = fieldValue;
                } else if (StringUtils.equals("WE_CHAT_BASE_URL", fieldName)) {
                    WE_CHAT_BASE_URL = fieldValue;
                } else if (StringUtils.equals("SERVICE_AGREEMENT", fieldName)) {
                    SERVICE_AGREEMENT = fieldValue;
                } else if (StringUtils.equals("PRIVACY_POLICY", fieldName)) {
                    PRIVACY_POLICY = fieldValue;
                }
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
