package com.lntu.online.util;

import com.lntu.online.util.gson.GsonWrapper;

import java.lang.reflect.Type;


public class JsonUtil {

    public static String toJson(Object src) {
        return GsonWrapper.gson.toJson(src);
    }

    public static <T>T fromJson(String json, Type typeOfT) {
        return GsonWrapper.gson.fromJson(json, typeOfT);
    }

}
