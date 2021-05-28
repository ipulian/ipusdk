package com.ipusoft.context.http;

/**
 * author : GWFan
 * time   : 5/27/21 2:21 PM
 * desc   :
 */

public class HttpConstant {
    /*
     * ContentType
     */
    public static final String CONTENT_TYPE = "Content-type:application/json;charset=UTF-8";

    public static final String LOG_CONFIG = "log_config";

    public static final String LOG_BODY = LOG_CONFIG + ":" + "BODY";

    public static final String LOG_NONE = LOG_CONFIG + ":" + "NONE";

    public static final String HOST_NAME = "host_name";
    /**
     * 对外接口
     */
    public static final String OPEN_URL = HOST_NAME + ":" + "https://api.51lianlian.cn";
    /*
     * 内部接口
     */
    public static final String INNER_BASE_URL = "https://presaas.51lianlian.cn";
}
