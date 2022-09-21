package com.ipusoft.context.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.ipusoft.context.R;
import com.ipusoft.utils.ResourceUtils;

/**
 * author : GWFan
 * time   : 9/14/21 5:54 PM
 * desc   :
 */

public class BaseDialogLinearLayout extends LinearLayout {
    private static final String TAG = "BaseDialogLinearLayout";

    public BaseDialogLinearLayout(Context context) {
        super(context);
    }

    public BaseDialogLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BaseDialogLinearLayout);
        if (array.hasValue(R.styleable.BaseDialogLinearLayout_android_background)) {
            setBackgroundColor(array.getColor(R.styleable.BaseDialogLinearLayout_android_background, ResourceUtils.getColor(R.color.black20)));
        } else {
            setBackgroundColor(ResourceUtils.getColor(R.color.black20));
        }
        array.recycle();
    }
}
