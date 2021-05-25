package com.ipusoft.context.utils;

import java.io.File;

/**
 * author : GWFan
 * time   : 5/25/21 11:55 AM
 * desc   :
 */

public class FileUtils {
    interface OnReplaceListener {
        boolean onReplace(File srcFile, File destFile);
    }

    public static String getFileExtension(File file) {
        return (file == null) ? "" : getFileExtension(file.getAbsolutePath());
    }

    public static String getFileExtension(String filePath) {
        if (StringUtils.isSpace(filePath)) return "";
        int lastPoi = filePath.lastIndexOf('.'),
                lastSep = filePath.lastIndexOf(File.separator);
        return (lastPoi == -1 || lastSep >= lastPoi) ? "" :
                filePath.substring(lastPoi + 1);
    }
}
