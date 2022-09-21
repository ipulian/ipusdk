package com.ipusoft.context.manager;

import android.content.Context;
import android.graphics.Typeface;

import com.ipusoft.context.AppContext;

/**
 * author : GWFan
 * time   : 11/5/21 5:05 PM
 * desc   : 字体管理类
 */

public class FontManager {
    private static final String TAG = "FontManager";
    public static final String RZZYT = "锐字真言体.ttf";
    public static final String MY_ICON_FONT = "MyIconFont.ttf";

    /**
     * <enum name="system" value="0" /><!--系统默认-->
     * <enum name="myiconfont" value="1" /><!--icon字体-->
     * <enum name="rzzy" value="2" /><!--瑞字真言-->
     * <enum name="serif" value="3" /><!--苹方-->
     * <enum name="sana_serif" value="4" /><!--roboto regular-->
     */
    public enum TypefaceEnum {
        SYSTEM(0),
        MYICONFONT(1),
        RZZY(2),
        SERIF(3),
        SANS_SERIF(4);

        private final int type;

        TypefaceEnum(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    public static Typeface getTypeface(TypefaceEnum typefaceEnum) {
        Typeface typeface = Typeface.DEFAULT;
        if (typefaceEnum == TypefaceEnum.MYICONFONT) {
            typeface = getIconFont(AppContext.getAppContext());
        } else if (typefaceEnum == TypefaceEnum.RZZY) {
            typeface = getRZZY(AppContext.getAppContext());
        } else if (typefaceEnum == TypefaceEnum.SERIF) {
            typeface = Typeface.SERIF;
        } else if (typefaceEnum == TypefaceEnum.SANS_SERIF) {
            typeface = Typeface.SANS_SERIF;
        }
        return typeface;
    }

    public static Typeface getTypeface(int type) {
        Typeface typeface = Typeface.DEFAULT;
        if (type == TypefaceEnum.MYICONFONT.type) {
            typeface = getIconFont(AppContext.getAppContext());
        } else if (type == TypefaceEnum.RZZY.type) {
            typeface = getRZZY(AppContext.getAppContext());
        } else if (type == TypefaceEnum.SERIF.type) {
            typeface = Typeface.SERIF;
        } else if (type == TypefaceEnum.SANS_SERIF.type) {
            typeface = Typeface.SANS_SERIF;
        }
        return typeface;
    }

    /**
     * 获取字体
     *
     * @param context 运行环境
     * @return 锐字真言体
     */
    public static Typeface getRZZY(Context context) {
        String name = "fonts/" + RZZYT;
        return Typeface.createFromAsset(context.getAssets(), name);
    }

    /**
     * 获取字体
     *
     * @param context 运行环境
     * @return IconFont
     */
    public static Typeface getIconFont(Context context) {
        String name = "fonts/" + MY_ICON_FONT;
        return Typeface.createFromAsset(context.getAssets(), name);
    }
}
