package org.lntu.online.model.api;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import org.lntu.online.R;
import org.lntu.online.display.activity.AuthErrorActivity;
import org.lntu.online.display.activity.OneKeyEvaActivity;
import org.lntu.online.display.dialog.DialogUtils;
import org.lntu.online.model.entity.ErrorInfo;
import org.lntu.online.model.storage.LoginShared;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class BackgroundCallback<T> implements Callback<T> {

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
                handleFailure(context.getString(R.string.auth_error));
                LoginShared.logout(context);
                Intent intent = new Intent(context, AuthErrorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
                break;
            case REMOTE_INVOKE_ERROR:
                handleFailure(context.getString(R.string.network_remote_invoke_error_tip));
                break;
            case NOT_EVALUATE:
                DialogUtils.createAlertDialogBuilder(context)
                        .setTitle("提示")
                        .setMessage("您本学期还没有完成评课，暂时无法查看成绩。")
                        .setPositiveButton("去评课", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.startActivity(new Intent(context, OneKeyEvaActivity.class));
                            }

                        })
                        .setNegativeButton("取消", null)
                        .show();
                handleFailure("您本学期还没有完成评课，暂时无法查看成绩");
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
