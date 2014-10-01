package com.lntu.online.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

public final class HttpUtil {

    private static AsyncHttpClient httpClient;

    public static synchronized AsyncHttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new AsyncHttpClient();
            httpClient.setTimeout(20000);
        }
        return httpClient;
    }

    public static RequestHandle baseGet(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        return getHttpClient().get(context, url, responseHandler);
    }

    public static RequestHandle baseGet(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        return getHttpClient().get(context, url, params, responseHandler);
    }

    public static RequestHandle basePost(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        return getHttpClient().post(context, url, params, responseHandler);
    }

    public static void get(Context context, String url, BaseListener listener) {
        RequestHandle requestHandle = getHttpClient().get(context, url, listener);
        listener.setRequestHandle(requestHandle);
    }

    public static void get(Context context, String url, RequestParams params, BaseListener listener) {
        RequestHandle requestHandle = getHttpClient().get(context, url, params, listener);
        listener.setRequestHandle(requestHandle);
    }

    public static void post(Context context, String url, RequestParams params, BaseListener listener) {
        RequestHandle requestHandle = getHttpClient().post(context, url, params, listener);
        listener.setRequestHandle(requestHandle);
    }

}
