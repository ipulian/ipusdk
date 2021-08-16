package com.ipusoft.logger;

import com.elvishew.xlog.flattener.PatternFlattener;

/**
 * author : GWFan
 * time   : 8/3/21 10:05 AM
 * desc   :
 */

public class LogFormatFlattener extends PatternFlattener {
    private static final String DEFAULT_PATTERN = "{d yyyy-MM-dd HH:mm:ss.SSS} {l}/{t}: {m}";

    public LogFormatFlattener() {
        super(DEFAULT_PATTERN);
    }
}
