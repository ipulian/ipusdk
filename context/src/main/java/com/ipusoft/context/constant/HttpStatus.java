package com.ipusoft.context.constant;

/**
 * author : GWFan
 * time   : 7/2/20 3:09 PM
 * desc   : 接口返回状态
 */

public class HttpStatus {
    /**
     * 请求成功
     */
    public static final String SUCCESS = "1";
    /**
     * 请求失败
     */
    public static final String FAILED = "0";
    /**
     * 身份过期
     */
    public static final String EXPIRED = "2";
    /**
     * 请求出错(客户端自定义)
     */
    public static final String ERROR = "3";
}