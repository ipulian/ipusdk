package com.ipusoft.context.component;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.ipusoft.utils.StringUtils;

/**
 * author : GWFan
 * time   : 2022/3/2 10:41
 * desc   :
 */

public class ITextView extends AppCompatTextView {
    public ITextView(@NonNull Context context) {
        super(context);
    }

    public ITextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ITextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (StringUtils.isEmpty(text)) {
            text = "";
        }
        super.setText(text, type);
    }
}
