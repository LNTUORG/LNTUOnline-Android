package com.lntu.online.model.api;

import android.content.Context;
import android.content.Intent;

import com.lntu.online.model.entity.ErrorInfo;
import com.lntu.online.shared.LoginShared;

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

    public void failure(ErrorInfo error) {
        switch (error.getErrorCode()) {
            case AUTH_ERROR: // 401认证错误
                LoginShared.logout(context); // 注销用户信息
                //Intent intent = new Intent(context, AuthErrorActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                //context.startActivity(intent);
                break;
            default:
                ToastUtils.with(context).show("网络访问错误，请检查网络");
                break;
        }
    }

}
