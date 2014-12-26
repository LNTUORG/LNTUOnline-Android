package com.lntu.online.http;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.TextHttpResponseHandler;

public abstract class BaseListener extends TextHttpResponseHandler {

    private Context context;
    private RequestHandle requestHandle;
    private ProgressDialog progressDialog;

    public BaseListener(Context context, boolean cancelable, String message) {
        this.context = context;
        progressDialog = new ProgressDialog(context);  
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(cancelable);
        if (cancelable) {
            progressDialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    if (getRequestHandle() != null) {
                        getRequestHandle().cancel(true);
                    }
                }

            });
        }
    }

    public BaseListener(Context context, boolean cancelable) {
        this(context, cancelable, "网络访问中,请稍后...");
    }

    public BaseListener(Context context) {
        this(context, true);
    }

    protected Context getContext() {
        return context;
    }

    protected void setRequestHandle(RequestHandle requestHandle) {
        this.requestHandle = requestHandle;
    }

    private RequestHandle getRequestHandle() {
        return requestHandle;
    }

    @Override
    public void onStart() {
        progressDialog.show();
    }

    @Override
    public void onFinish() {
        progressDialog.dismiss();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
    }

    @Override
    public void onCancel() {
    }

}
