package org.lntu.online.model.api;

import android.content.Context;
import android.content.Intent;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lntu.online.R;
import org.lntu.online.model.entity.ErrorInfo;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.activity.AuthErrorActivity;
import org.lntu.online.ui.activity.OneKeyEvaActivity;
import org.lntu.online.util.ShipUtils;
import org.lntu.online.util.ToastUtils;

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
                new MaterialDialog.Builder(context)
                        .title("提示")
                        .content("您本学期还没有完成评课，暂时无法查看成绩。")
                        .contentColorRes(R.color.text_color_primary)
                        .positiveText("去评课")
                        .positiveColorRes(R.color.color_primary)
                        .negativeText("取消")
                        .negativeColorRes(R.color.text_color_primary)
                        .callback(new MaterialDialog.ButtonCallback() {

                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                context.startActivity(new Intent(context, OneKeyEvaActivity.class));
                            }

                        })
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
