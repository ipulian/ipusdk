package com.ipusoft.utils;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author : GWFan
 * time   : 7/8/21 10:55 PM
 * desc   :
 */

public class ViewUtils {
    /**
     * 获取传入指定视图下的所有子视图
     *
     * @param view 根视图
     * @return 根视图的所有子视图
     */
    public static List<View> getAllChildViews(View view) {
        List<View> allChildViews = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewChild = vp.getChildAt(i);
                allChildViews.add(viewChild);
                allChildViews.addAll(getAllChildViews(viewChild));
            }
        }
        return allChildViews;
    }
}
