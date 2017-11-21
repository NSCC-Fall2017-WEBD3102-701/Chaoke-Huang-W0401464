package com.webd3102;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonDeserializer {
    private Gson gson = new Gson();


    public Object parseInvoices(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        Type listType = new TypeToken<List<Invoice>>(){}.getType();
        return gson.fromJson(s, listType);
    }
}
