package com.ipusoft.context.help;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;

import com.ipusoft.context.R;
import com.ipusoft.utils.ResourceUtils;

/**
 * author : GWFan
 * time   : 6/11/21 4:22 PM
 * desc   :
 */

public class EditTextHelper {
    /**
     * 设置EditTextHint字体大小
     *
     * @param editText
     * @return
     */

    public static void setHintTextSize(EditText editText, String hint) {
        SpannableString ss = new SpannableString(hint);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }

    public static void setHintTextSize(EditText editText, int fontSize, String hintText) {
        SpannableString ss = new SpannableString(hintText);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(fontSize, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }

    public static void setHintTextSize(int fontSize, EditText... editTexts) {
        SpannableString ss = new SpannableString(ResourceUtils.getString(R.string.please_input));//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(fontSize, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (EditText editText : editTexts) {
            editText.setHint(new SpannedString(ss));
        }
    }
}
