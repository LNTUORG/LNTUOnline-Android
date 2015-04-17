package com.lntu.online.model.api;

import android.content.Context;
import android.content.Intent;

import com.lntu.online.R;
import com.lntu.online.model.entity.ErrorInfo;
import com.lntu.online.shared.LoginShared;

import com.lntu.online.ui.activity.AuthErrorActivity;
import com.lntu.online.util.ToastUtils;

import retrofit.RetrofitError;

public class DefaultCallback<T> extends CallbackAdapter<T> {

    private Context context;

    public DefaultCallback(Context context) {
        this.context = context;
    }

    @Override
    public final void failure(RetrofitError error) {
        failure(ErrorInfo.build(error));
    }

    public void failure(ErrorInfo errorInfo) {
        switch (errorInfo.getErrorCode()) {
            case REMOTE_INVOKE_ERROR:
                ToastUtils.with(context).show(R.string.network_remote_invoke_error_tip);
                break;
            case AUTH_ERROR: // 401认证错误
                LoginShared.logout(context);
                Intent intent = new Intent(context, AuthErrorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
                break;
            default:
                if (errorInfo.getStatusCode() >= 500) {
                    ToastUtils.with(context).show(R.string.network_server_error_tip);
                } else {
                    ToastUtils.with(context).show(R.string.network_default_error_tip);
                }
                break;
        }
    }

}
