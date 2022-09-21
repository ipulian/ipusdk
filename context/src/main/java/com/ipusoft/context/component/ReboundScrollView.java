package com.ipusoft.context.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ReboundScrollView extends ScrollView {
    private static final int MAX_SCROLL = 200;
    private static final float SCROLL_RATIO = 0.5f;// 阻尼系数

    public ReboundScrollView(Context context) {
        super(context);
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReboundScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        int newDeltaY = deltaY;
        int delta = (int) (deltaY * SCROLL_RATIO);
        if ((scrollY + deltaY) == 0 || (scrollY - scrollRangeY + deltaY) == 0) {
            newDeltaY = deltaY;   //回弹最后一次滚动，复位
        } else {
            newDeltaY = delta;   //增加阻尼效果
        }
        return super.overScrollBy(deltaX, newDeltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, MAX_SCROLL, isTouchEvent);
    }
} 