package com.ipusoft.context.bean.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.ipusoft.logger.XLogger;
import com.ipusoft.utils.StringUtils;
import com.ipusoft.utils.Utils;

import java.io.IOException;

/**
 * author : GWFan
 * time   : 1/23/21 11:25 AM
 * desc   : 将服务端返回的数据类型String转成Long
 */

public class String2LongAdapter extends TypeAdapter<Long> {
    private static final String TAG = "String2LongAdapter";

    @Override
    public void write(JsonWriter out, Long value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value);
        }
    }

    @Override
    public Long read(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        long result = 0;
        switch (peek) {
            case STRING:
                String s = in.nextString();
                if (StringUtils.isNotEmpty(s)) {
                    try {
                        result = Long.parseLong(s);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case NUMBER:
                try {
                    result = Utils.parseLong(in.nextLong());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                XLogger.e("Json 类型转换错误：Expected STRING or NUMBER but was " + peek);
                //  throw new JsonParseException("Json 类型转换错误：Expected STRING or NUMBER but was " + peek);
        }
        return result;
    }
}
