package com.lntu.online.model.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

public final class GsonWrapper {

    private GsonWrapper() {}

    public final static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .create();

}
