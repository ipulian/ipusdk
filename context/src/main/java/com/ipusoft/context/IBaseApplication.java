package com.ipusoft.context;

/**
 * author : GWFan
 * time   : 5/18/21 10:09 AM
 * desc   :
 */

public interface IBaseApplication {
    /**
     * 初始化对应的module
     */
    void initModule();

    /**
     * 清除module的数据
     */
    void unInitModule();
}
