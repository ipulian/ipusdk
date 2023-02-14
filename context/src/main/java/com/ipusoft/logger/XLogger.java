package com.ipusoft.logger;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.formatter.border.DefaultBorderFormatter;
import com.elvishew.xlog.formatter.message.json.DefaultJsonFormatter;
import com.elvishew.xlog.formatter.message.throwable.DefaultThrowableFormatter;
import com.elvishew.xlog.formatter.message.xml.DefaultXmlFormatter;
import com.elvishew.xlog.formatter.stacktrace.DefaultStackTraceFormatter;
import com.elvishew.xlog.formatter.thread.DefaultThreadFormatter;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy;
import com.elvishew.xlog.printer.file.clean.NeverCleanStrategy;
import com.elvishew.xlog.printer.file.writer.SimpleWriter;
import com.ipusoft.context.AppContext;
import com.ipusoft.context.AppRuntimeContext;
import com.ipusoft.utils.AppUtils;
import com.ipusoft.utils.DeviceUtils;
import com.ipusoft.utils.ExceptionUtils;
import com.ipusoft.utils.FileUtilsKt;
import com.ipusoft.utils.StringUtils;

import java.io.File;

/**
 * author : GWFan
 * time   : 8/2/21 3:37 PM
 * desc   :
 */

public class XLogger {
    private static final String TAG = "XLogger";

    public static void initXLog() {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(LogLevel.ALL)           // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
                .tag("")                                         // 指定 TAG，默认为 "X-LOG"
                // .enableThreadInfo()                                    // 允许打印线程信息，默认禁止
                .enableStackTrace(2)                                   // 允许打印深度为 2 的调用栈信息，默认禁止
                // .enableBorder()                                        // 允许打印日志边框，默认禁止
                .jsonFormatter(new DefaultJsonFormatter())                  // 指定 JSON 格式化器，默认为 DefaultJsonFormatter
                .xmlFormatter(new DefaultXmlFormatter())                    // 指定 XML 格式化器，默认为 DefaultXmlFormatter
                .throwableFormatter(new DefaultThrowableFormatter())        // 指定可抛出异常格式化器，默认为 DefaultThrowableFormatter
                .threadFormatter(new DefaultThreadFormatter())              // 指定线程信息格式化器，默认为 DefaultThreadFormatter
                .stackTraceFormatter(new DefaultStackTraceFormatter())      // 指定调用栈信息格式化器，默认为 DefaultStackTraceFormatter
                .borderFormatter(new DefaultBorderFormatter())               // 指定边框格式化器，默认为 DefaultBorderFormatter
//                .addObjectFormatter(Object.class,                    // 为指定类型添加对象格式化器
//                        new AnyClassObjectFormatter())                     // 默认使用 Object.toString()
//                .addInterceptor(new BlacklistTagsFilterInterceptor(    // 添加黑名单 TAG 过滤器
//                        "blacklist1", "blacklist2", "blacklist3"))
//                .addInterceptor(new MyInterceptor())                   // 添加一个日志拦截器
                .build();

        String logPath = FileUtilsKt.getLogPath(AppContext.getAppContext());
        Printer androidPrinter = new AndroidPrinter(true);         // 通过 android.util.Log 打印日志的打印器
        //  Printer consolePrinter = new ConsolePrinter();             // 通过 System.out 打印日志到控制台的打印器
        Printer filePrinter = new FilePrinter                      // 打印日志到文件的打印器
                .Builder(logPath)// 指定保存日志文件的路径
                .fileNameGenerator(new LogFileGenerator())        // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
                .backupStrategy(new NeverBackupStrategy())             // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
                .cleanStrategy(new NeverCleanStrategy())     // 指定日志文件清除策略，默认为 NeverCleanStrategy()
                .flattener(new LogFormatFlattener())                          // 指定日志平铺器，默认为 DefaultFlattener
                .writer(new SimpleWriter())                                // 指定日志写入器，默认为 SimpleWriter
                .build();
        XLog.init(                                                 // 初始化 XLog
                config,                                                // 指定日志配置，如果不指定，会默认使用 new LogConfiguration.Builder().build()
                androidPrinter, filePrinter);                               // 添加任意多的打印器。如果没有添加任何打印器，会默认使用 AndroidPrinter(Android)/ConsolePrinter(java)
        String fileName = LogFileGenerator.getInstance().generateFileName(LogLevel.ALL, System.currentTimeMillis());
        boolean fileExists = FileUtilsKt.isFileExists(logPath + "/" + fileName);
        if (!fileExists || new File(logPath + "/" + fileName).length() == 0) {
            String systemLanguage = DeviceUtils.getSystemLanguage();
            String systemVersion = DeviceUtils.getSystemVersion();
            String systemModel = DeviceUtils.getSystemModel();
            String systemDevice = DeviceUtils.getSystemDevice();
            String deviceBrand = DeviceUtils.getDeviceBrand();
            String deviceManufacturer = DeviceUtils.getDeviceManufacturer();
            String appName = AppUtils.getAppName();
            String pkgName = AppUtils.getAppPackageName();
            String appVersionName = AppUtils.getAppVersionName();
            XLog.d("系统语言：%s\n系统版本：%s\n手机型号：%s\n设备名称：%s\n手机厂商：%s\n手机厂商名称：%s\n版本名称：%s\n应用包名：%s\n应用版本号：%s\n",
                    systemLanguage, systemVersion, systemModel, systemDevice,
                    deviceBrand, deviceManufacturer, appVersionName, appName, pkgName
            );
        }
    }

    public static void e(String msg) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.e(msg);
    }

    public static void e(String msg, Throwable tr) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.e(msg, tr);
    }

    /**
     * 支持所有的 Collection 和 Map 类型的数据。
     * 也可以直接打印 Intent 和 Bundle 对象。
     *
     * @param object
     */
    public static void e(Object object) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.e(object);
    }

    public static void e(Object[] objects) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.e(objects);
    }

    public static void i(String msg) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.i(msg);
    }

    public static void i(String msg, Throwable tr) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.i(msg, tr);
    }

    public static void i(Object object) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.i(object);
    }

    public static void i(Object[] objects) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.i(objects);
    }

    public static void d(String msg) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.d(msg);
    }

    public static void d(String tag, String msg) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.d(tag + msg);
    }

    public static void d(String msg, Throwable tr) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.d(msg, tr);
    }

    public static void d(Object object) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.d(object);
    }

    public static void d(Object[] objects) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.d(objects);
    }

    public static void json(String json) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.json(json);
    }

    public static void xml(String xml) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        XLog.xml(xml);
    }

    /**
     * 将未捕获异常单独输出
     */
    public static void writeErrorImmediately(Throwable throwable) {
        if (StringUtils.equals("false", AppRuntimeContext.isDebug())) {
            return;
        }
        String logPath = FileUtilsKt.getLogErrorPath(AppContext.getAppContext());
        Printer filePrinter = new FilePrinter.Builder(logPath)
                .fileNameGenerator(new LogErrorFileGenerator())
                .backupStrategy(new NeverBackupStrategy())
                .cleanStrategy(new NeverCleanStrategy())
                .flattener(new LogFormatFlattener())
                .writer(new SimpleWriter())
                .build();
        Logger logger = XLog.printers(filePrinter)
                .enableStackTrace(5)
                .enableThreadInfo()
                .build();
        logger.e(ExceptionUtils.getErrorInfo(throwable) + "\n\n\n\n\n");
        logger.e(throwable);
        String fileName = LogErrorFileGenerator.getInstance().generateFileName(LogLevel.ALL, System.currentTimeMillis());
        boolean fileExists = FileUtilsKt.isFileExists(logPath + "/" + fileName);
        if (!fileExists || new File(logPath + "/" + fileName).length() == 0) {
            String systemLanguage = DeviceUtils.getSystemLanguage();
            String systemVersion = DeviceUtils.getSystemVersion();
            String systemModel = DeviceUtils.getSystemModel();
            String systemDevice = DeviceUtils.getSystemDevice();
            String deviceBrand = DeviceUtils.getDeviceBrand();
            String deviceManufacturer = DeviceUtils.getDeviceManufacturer();
            String appName = AppUtils.getAppName();
            String pkgName = AppUtils.getAppPackageName();
            String appVersionName = AppUtils.getAppVersionName();
            logger.e("系统语言：%s\n系统版本：%s\n手机型号：%s\n设备名称：%s\n手机厂商：%s\n手机厂商名称：%s\n版本名称：%s\n应用包名：%s\n应用版本号：%s\n",
                    systemLanguage, systemVersion, systemModel, systemDevice,
                    deviceBrand, deviceManufacturer, appVersionName, appName, pkgName
            );
        }
    }
}
