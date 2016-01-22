package org.lntu.online.model.api;

import org.lntu.online.BuildConfig;
import org.lntu.online.util.gson.GsonWrapper;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public final class ApiClient {

    private ApiClient() {}

    public static final ApiService service;

    static {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ApiDefine.API_HOST)
                .setConverter(new GsonConverter(GsonWrapper.gson))
                .setRequestInterceptor(new ApiRequestInterceptor())
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();

        service = restAdapter.create(ApiService.class);
    }

}
