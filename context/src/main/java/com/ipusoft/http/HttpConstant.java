package com.ipusoft.http;

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
    public static final String HOST_NAME = "host_name";
    public static final String OPEN = "open";
    public static final String GATEWAY = "gateway";

    public static final String OPEN_URL = HOST_NAME + ":" + OPEN;
    public static final String GATEWAY_URL = HOST_NAME + ":" + GATEWAY;

    /**
     * 对外接口预发布
     */
    public static final String OPEN_URL_DEV = "https://preapi.51lianlian.cn";
    /**
     * 对外接口正式
     */
    public static final String OPEN_URL_PRO = "https://api.51lianlian.cn";


    /*
     * 内部接口预发布
     */
    public static final String INNER_BASE_URL_DEV = "https://presaas.51lianlian.cn";
    /**
     * 内部接口
     */
    public static final String INNER_BASE_URL_PRO = "https://saas.51lianlian.cn";
}
