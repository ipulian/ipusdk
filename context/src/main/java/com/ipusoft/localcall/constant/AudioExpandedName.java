package com.ipusoft.localcall.constant;


import com.ipusoft.utils.FileUtilsKt;
import com.ipusoft.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author : GWFan
 * time   : 3/31/21 6:15 PM
 * desc   : 常见的音频类型扩展名
 */

public enum AudioExpandedName {
    MP3("MP3"),
    M4A("M4A"),
    WMA("WMA"),
    APE("APE"),
    WAV("WAV"),
    FLAC("FLAC"),
    RM("RM"),
    RA("RA"),
    AAC("AAC");

    private final String name;

    AudioExpandedName(String name) {
        this.name = name;
    }

    private String getName() {
        return this.name;
    }

    /**
     * 获取所有的扩展名
     *
     * @return
     */
    public static List<String> getExpandedNameList() {
        ArrayList<String> list = new ArrayList<>();
        for (AudioExpandedName item : AudioExpandedName.values()) {
            list.add(item.getName());
        }
        return list;
    }

    /**
     * 音频扩展名最大长度
     *
     * @return
     */
    public static int getExpandedNameMaxLength() {
        int length = 0;
        for (AudioExpandedName item : AudioExpandedName.values()) {
            if (item.name.length() > length) {
                length = item.name.length();
            }
        }
        return length;
    }

    /**
     * 是否包含指定扩展名
     *
     * @param file
     * @return
     */
    public static boolean judgeExpandedName(File file) {
        boolean flag = false;
        if (file != null) {
            String fileExtension = FileUtilsKt.getFileExtension(file);
            if (StringUtils.isNotEmpty(fileExtension)) {
                fileExtension = fileExtension.toUpperCase();
                if (getExpandedNameMaxLength() >= fileExtension.length()
                        && getExpandedNameList().contains(fileExtension)) {
                    flag = true;
                }
            }
        }
        return flag;
    }
}
