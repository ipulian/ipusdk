package com.ipusoft.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * author : GWFan
 * time   : 8/5/21 9:29 AM
 * desc   :
 */

public class ExceptionUtils {

    /**
     * 获取异常的详细信息
     *
     * @param thr Throwable
     * @return 异常详细信息
     */
    public static String getErrorInfo(Throwable thr) {
        StringWriter stringWriter = new StringWriter();
        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            thr.printStackTrace(printWriter);
            return stringWriter.toString();
        }
    }
}
