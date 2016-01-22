package org.lntu.online.model.api;

import android.os.Build;

import org.lntu.online.BuildConfig;

public final class ApiDefine {

    private ApiDefine() {}

    public static final String USER_AGENT = "LNTUOnline/" + BuildConfig.VERSION_NAME + " (Android " + Build.VERSION.RELEASE + "; " + Build.MANUFACTURER + " - " + Build.MODEL + ")";
    public static final String HTTP_ACCEPT = "application/json";

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String API_HOST = "https://api.online.lntu.org";

}
