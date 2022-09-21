package com.ipusoft.context.manager;

/**
 * author : GWFan
 * time   : 8/25/21 10:41 AM
 * desc   : 视图管理接口
 */

public interface ViewManager {

    //显示
    void show();

    //隐藏
    void dismiss();

    //切换状态
    int toggle();
}
