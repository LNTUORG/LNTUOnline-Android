package com.lntu.online.util;

import java.lang.reflect.Type;
import com.google.gson.Gson;

public class JsonUtil {

    private static final Gson gson = new Gson();
    //private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static <T>T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

}
