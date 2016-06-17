package org.lntu.online.model.api;

import android.content.Context;

import org.lntu.online.R;
import org.lntu.online.model.entity.ErrorInfo;
import org.lntu.online.ui.dialog.DialogUtils;
import org.lntu.online.ui.dialog.ProgressDialog;

import retrofit.client.Response;

public class DialogCallback<T> extends DefaultCallback<T> {

    private ProgressDialog progressDialog;

    public DialogCallback(Context context) {
        super(context);
        progressDialog = DialogUtils.createProgressDialog(context);
        progressDialog.setMessage(R.string.networking);
        progressDialog.setCancelable(false);
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
