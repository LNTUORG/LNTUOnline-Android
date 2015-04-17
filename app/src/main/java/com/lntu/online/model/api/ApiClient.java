package com.lntu.online.model.api;

import android.content.Context;

import com.lntu.online.config.NetworkInfo;
import com.lntu.online.model.gson.GsonWrapper;
import com.lntu.online.util.AppUtils;

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
                .setEndpoint(NetworkInfo.API_HOST)
                .setConverter(new GsonConverter(GsonWrapper.gson))
                .setRequestInterceptor(new ApiRequestInterceptor(AppUtils.getVersionName(context)))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        apiService = restAdapter.create(ApiService.class);
    }

}
