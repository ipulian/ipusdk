package com.ipusoft.context;

import com.ipusoft.context.config.Env;
import com.ipusoft.utils.StringUtils;
import com.ipusoft.http.HttpConstant;
import com.ipusoft.http.manager.RetrofitManager;

/**
 * author : GWFan
 * time   : 6/9/21 11:50 AM
 * desc   :
 */

public class AppRuntimeContext extends AppContext {
    private static final String TAG = "AppRuntimeContext";
    /**
     * SDK运行环境
     */
    protected static String env;

    protected static String isDebug = "false";

    public static String BASE_URL = "";

    public static String OPEN_BASE_URL = "";

    public static String GATE_WAY_URL = "";

    public static String WE_CHAT_BASE_URL = "";

    /**
     * @param env 设置运行环境,外部调用
     */
    protected static void setRuntimeEnv(String env) {
        AppRuntimeContext.env = env;
        if (StringUtils.equals(Env.OPEN_DEV, env)) {
            BASE_URL = HttpConstant.INNER_BASE_URL_DEV;
            OPEN_BASE_URL = HttpConstant.OPEN_URL_DEV;
        } else if (StringUtils.equals(Env.OPEN_PRO, env)) {
            BASE_URL = HttpConstant.INNER_BASE_URL_PRO;
            OPEN_BASE_URL = HttpConstant.OPEN_URL_PRO;
        }
    }

    /**
     * @return 运行环境
     */
    public static String getRuntimeEnv() {
        return AppRuntimeContext.env;
    }


    /**
     * 初始化Http环境
     */
    protected static void initHttpEnvironment() {
        RetrofitManager.getInstance().initRetrofit();
    }

    public static String isDebug() {
        return AppRuntimeContext.isDebug;
    }
}
