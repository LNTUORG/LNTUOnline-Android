package com.lntu.online.model.api;

import android.content.Context;
import android.content.Intent;

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
                ToastUtils.with(context).show("远程调用失败，教务在线服务器可能挂掉了...");
                break;
            case AUTH_ERROR: // 401认证错误
                LoginShared.logout(context);
                Intent intent = new Intent(context, AuthErrorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
                break;
            default:
                if (errorInfo.getStatusCode() >= 500) {
                    ToastUtils.with(context).show("服务器被外星人搬走啦...");
                } else {
                    ToastUtils.with(context).show("网络通信失败，请检查一下网络连接");
                }
                break;
        }
    }

}
