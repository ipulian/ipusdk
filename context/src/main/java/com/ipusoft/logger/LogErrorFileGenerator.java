package com.ipusoft.logger;

import com.elvishew.xlog.printer.file.naming.FileNameGenerator;
import com.ipusoft.context.constant.DateTimePattern;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * author : GWFan
 * time   : 8/3/21 9:14 AM
 * desc   : 日志名称生成器
 */

public class LogErrorFileGenerator implements FileNameGenerator {

    private static class LogErrorFileGeneratorHolder {
        private static final LogErrorFileGenerator INSTANCE = new LogErrorFileGenerator();
    }

    public static LogErrorFileGenerator getInstance() {
        return LogErrorFileGeneratorHolder.INSTANCE;
    }

    @Override
    public boolean isFileNameChangeable() {
        return true;
    }

    @Override
    public String generateFileName(int logLevel, long timestamp) {
        SimpleDateFormat sdf = DateTimePattern.getDateTimeWithSecondFormat();
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
