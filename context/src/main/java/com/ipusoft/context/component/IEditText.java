package com.ipusoft.context.component;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.ipusoft.context.R;
import com.ipusoft.utils.ResourceUtils;

/**
 * author : GWFan
 * time   : 2020/5/26 09:11
 * desc   :
 */

public class IEditText extends AppCompatEditText {

    public IEditText(@NonNull Context context) {
        super(context);
        initView();
    }

    public IEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setTextCursorDrawable(ResourceUtils.getDrawable(R.drawable.bg_et_cursor));
        }
        setBackground(null);
    }

    public interface OnTextChangedListener {
        void onTextChanged(EditText editText, String str);
    }

    public void setOnTextChangedListener(OnTextChangedListener listener) {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (listener != null) {
                    listener.onTextChanged(IEditText.this, s.toString());
                }
            }
        });
    }

    public String getValue() {
        String value = "";
        Editable text = getText();
        if (text != null) {
            value = text.toString();
        }
        return value;
    }
}
