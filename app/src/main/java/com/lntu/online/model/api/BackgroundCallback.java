package com.lntu.online.model.api;

import android.content.Context;
import android.content.Intent;

import com.lntu.online.R;
import com.lntu.online.model.entity.ErrorInfo;
import com.lntu.online.shared.LoginShared;
import com.lntu.online.ui.activity.AuthErrorActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BackgroundCallback<T> implements Callback<T> {

    private Context context;

    public BackgroundCallback(Context context) {
        this.context = context;
    }

    @Override
    public final void success(T t, Response response) {
        handleSuccess(t, response);
    }

    @Override
    public final void failure(RetrofitError error) {
        ErrorInfo errorInfo = ErrorInfo.build(error);
        switch (errorInfo.getErrorCode()) {
            case AUTH_ERROR: // 401认证错误
                LoginShared.logout(context);
                Intent intent = new Intent(context, AuthErrorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
                break;
            case REMOTE_INVOKE_ERROR:
                handleFailure(context.getString(R.string.network_remote_invoke_error_tip));
                break;
            default:
                if (errorInfo.getStatusCode() >= 500) {
                    handleFailure(context.getString(R.string.network_server_error_tip));
                } else {
                    handleFailure(context.getString(R.string.network_default_error_tip));
                }
                break;
        }
    }

    public void handleSuccess(T t, Response response) {}

    public void handleFailure(String message) {}

}
