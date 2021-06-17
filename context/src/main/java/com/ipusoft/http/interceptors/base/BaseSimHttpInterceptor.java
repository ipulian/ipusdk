package com.ipusoft.http.interceptors.base;

import java.util.Map;

/**
 * author : GWFan
 * time   : 5/30/21 2:17 PM
 * desc   : 主卡外呼，网络请求的拦截器
 */

public abstract class BaseSimHttpInterceptor {

    public abstract void interceptor(Map<String, Object> params);
}
