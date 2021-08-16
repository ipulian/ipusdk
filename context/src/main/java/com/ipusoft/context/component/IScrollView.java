package com.ipusoft.context.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import com.ipusoft.context.AppContext;

/**
 * author : GWFan
 * time   : 8/6/21 3:09 PM
 * desc   :
 */

public class IScrollView extends ScrollView implements View.OnTouchListener {
    public IScrollView(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public IScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event != null) {
            if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
                InputMethodManager imm = ((InputMethodManager) AppContext.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                boolean isKeyboardUp = imm.isAcceptingText();
                if (isKeyboardUp) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return false;
    }
}
