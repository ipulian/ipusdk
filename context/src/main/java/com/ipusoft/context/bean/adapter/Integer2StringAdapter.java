package com.ipusoft.context.bean.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * author : GWFan
 * time   : 1/23/21 11:25 AM
 * desc   : 将服务端返回的数据类型Integer转成String
 */

public class Integer2StringAdapter extends TypeAdapter<String> {
    private static final String TAG = "Integer2StringAdapter";

    @Override
    public void write(JsonWriter out, String value) throws IOException {
        if (value == null) {
            out.value("");
        } else {
            out.value(value);
        }
    }

    @Override
    public String read(JsonReader in) throws IOException {
        JsonToken peek = in.peek();
        String result = "1";
        if (peek == JsonToken.NUMBER) {
            int i = in.nextInt();
            try {
                result = String.valueOf(i);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if (peek == JsonToken.STRING) {
            result = in.nextString();
        }
        return result;
    }
}
