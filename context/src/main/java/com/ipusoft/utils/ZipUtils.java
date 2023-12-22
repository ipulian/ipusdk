package com.ipusoft.utils;

import com.ipusoft.utils.ArrayUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * author : GWFan
 * time   : 8/3/21 6:57 PM
 * desc   : 文件压缩工具类
 */

public class ZipUtils {
    private static final String TAG = "ZipUtils";

    /**
     * 文件压缩
     *
     * @param zipFile  zip文件
     * @param srcFiles 源文件
     */
    public static void zipFolder(File zipFile, File... srcFiles) {
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            if (ArrayUtils.isNotEmpty(srcFiles)) {
                for (File file : srcFiles) {
                    if (file != null && file.exists()) {
                        ZipEntry zipEntry = new ZipEntry(file.getName());
                        FileInputStream inputStream = new FileInputStream(file);
                        zipOutputStream.putNextEntry(zipEntry);
                        int len;
                        byte[] buffer = new byte[4096];
                        while ((len = inputStream.read(buffer)) != -1) {
                            zipOutputStream.write(buffer, 0, len);
                        }
                        zipOutputStream.closeEntry();
                    }
                }
                zipOutputStream.finish();
                zipOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
