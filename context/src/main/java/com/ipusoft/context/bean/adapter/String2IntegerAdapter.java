package com.ipusoft.context.bean.adapter;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * author : GWFan
 * time   : 1/23/21 11:25 AM
 * desc   : 将服务端返回的数据类型String转成Integer
 */

public class String2IntegerAdapter extends TypeAdapter<Integer> {
    private static final String TAG = "String2IntegerAdapter";

    @Override
    public void write(JsonWriter out, Integer value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value);
        }
    }

    @Override
    public Integer read(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        int result = 0;
        switch (peek) {
            case STRING:
                String s = in.nextString();
                if (s != null && !"".equals(s)) {
                    try {
                        result = Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case NUMBER:
                result = in.nextInt();
                break;
            default:
                throw new JsonParseException("Json 类型转换错误：Expected STRING or NUMBER but was " + peek);
        }
        return result;
    }
}
