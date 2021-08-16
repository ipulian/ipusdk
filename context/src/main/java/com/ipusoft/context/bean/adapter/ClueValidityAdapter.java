package com.ipusoft.context.bean.adapter;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.ipusoft.context.constant.Validity;

import java.io.IOException;

/**
 * author : GWFan
 * time   : 1/23/21 5:22 PM
 * desc   : 线索有效性的Adapter
 */

public class ClueValidityAdapter extends TypeAdapter<String> {
    private static final String TAG = "ClueValidityAdapter";

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
        String result = "";
        JsonToken peek = in.peek();
        if (peek == JsonToken.NUMBER) {
            result = Validity.getKeyByValue(in.nextInt());
        } else if (peek == JsonToken.STRING) {
            result = in.nextString();
        } else {
            throw new JsonParseException("ClueValidityAdapter 类型转换错误: " + peek);
        }
        return result;
    }
}
