package org.lntu.online.model.api;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import org.lntu.online.R;
import org.lntu.online.model.entity.ErrorInfo;

import retrofit.client.Response;

public class DialogCallback<T> extends DefaultCallback<T> {

    private MaterialDialog progressDialog;

    public DialogCallback(Context context) {
        super(context);
        progressDialog = new MaterialDialog.Builder(context)
                .content(R.string.networking)
                .progress(true, 0)
                .cancelable(false)
                .build();
        progressDialog.show();
    }

    @Override
    public final void success(T t, Response response) {
        progressDialog.dismiss();
        handleSuccess(t, response);
    }

    @Override
    public final void failure(ErrorInfo errorInfo) {
        progressDialog.dismiss();
        handleFailure(errorInfo);
    }

    public void handleSuccess(T t, Response response) {
        super.success(t, response);
    }

    public void handleFailure(ErrorInfo errorInfo) {
        super.failure(errorInfo);
    }

}
