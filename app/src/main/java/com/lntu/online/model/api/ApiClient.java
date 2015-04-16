package com.lntu.online.model.api;

import android.content.Context;

import com.lntu.online.util.AppUtils;
import com.lntu.online.util.gson.GsonWrapper;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class ApiClient {

    private volatile static ApiClient singleton;

    public static ApiClient with(Context context) {
        if (singleton == null) {
            synchronized (ApiClient.class) {
                if (singleton == null) {
                    singleton = new ApiClient(context);
                }
            }
        }
        return singleton;
    }

    public final ApiService apiService;

    private ApiClient(Context context) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://lntuonline.coding.io/api")
                .setConverter(new GsonConverter(GsonWrapper.gson))
                .setRequestInterceptor(new ApiRequestInterceptor(AppUtils.getVersionName(context)))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        apiService = restAdapter.create(ApiService.class);
    }

}
