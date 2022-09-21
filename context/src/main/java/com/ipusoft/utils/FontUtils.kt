package com.ipusoft.utils

import android.graphics.Typeface
import android.widget.TextView

/**
 * author : GWFan
 * time   : 2020/4/30 15:01
 * desc   :
 */

object FontUtils {

    /**
     * 设置字体文件
     * @param tView
     * @param fontFamily
     * @param fontName
     */
    fun setTextFont(tView: TextView?, fontFamily: String, fontName: String?) {
        if (tView == null) {
            return
        }
        try {
            val name = "fonts/$fontFamily.ttf"
            val tf = Typeface.createFromAsset(tView.context.assets, name)
            tView.typeface = tf
            tView.text = fontName
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setTextFont(tView: TextView?, fontName: String?) {
        if (tView == null) {
            return
        }
        try {
            tView.text = fontName
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setTextFont(tView: TextView?, fontFamily: String, fontName: String?, color: Int) {
        if (tView == null) {
            return
        }
        try {
            val name = "fonts/$fontFamily.ttf"
            val tf = Typeface.createFromAsset(tView.context.assets, name)
            tView.typeface = tf
            tView.text = fontName
            tView.setTextColor(color)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setTextFont(tView: TextView?, fontName: String?, color: Int) {
        if (tView == null) {
            return
        }
        try {
            tView.text = fontName
            tView.setTextColor(color)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}