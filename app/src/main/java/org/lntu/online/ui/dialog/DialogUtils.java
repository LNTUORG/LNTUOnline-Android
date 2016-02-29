package org.lntu.online.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import org.lntu.online.R;

public final class DialogUtils {

    private DialogUtils() {}

    public static ProgressDialog createProgressDialog(Context context) {
        return new ProgressDialog(context, R.style.AppDialog);
    }

    public static AlertDialog.Builder createAlertDialogBuilder(Context context) {
        return new AlertDialog.Builder(context, R.style.AppDialog);
    }

}
