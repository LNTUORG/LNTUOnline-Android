package org.lntu.online.model.api;

import android.os.Build;

import retrofit.RequestInterceptor;

public class ApiRequestInterceptor implements RequestInterceptor {

    private final static String APPLICATION_JSON = "application/json";
    private final String USER_AGENT;

    public ApiRequestInterceptor(String versionName) {
        USER_AGENT = "LntuOnline/" + versionName + " (Android " + Build.VERSION.RELEASE + ", " + Build.MANUFACTURER + "-" + Build.MODEL + ")";
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Accept", APPLICATION_JSON);
    }

}
