package org.lntu.online.model.api;

import org.lntu.online.BuildConfig;
import org.lntu.online.util.gson.GsonWrapper;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public final class ApiClient {

    private ApiClient() {}

    // private static final String API_HOST = "https://api.online.lntu.org";
    private static final String API_HOST = "http://lntuonline-api.takwolf.com";
    // private static final String API_HOST = "http://192.168.1.108:8080";

    public static final ApiService service = new RestAdapter.Builder()
            .setEndpoint(API_HOST)
            .setConverter(new GsonConverter(GsonWrapper.gson))
            .setRequestInterceptor(new ApiRequestInterceptor())
            .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
            .build()
            .create(ApiService.class);

}
