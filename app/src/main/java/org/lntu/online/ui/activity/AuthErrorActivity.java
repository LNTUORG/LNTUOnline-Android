package org.lntu.online.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import org.lntu.online.R;
import org.lntu.online.ui.base.FullLayoutActivity;
import org.lntu.online.ui.dialog.DialogUtils;

public class AuthErrorActivity extends FullLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DialogUtils.createAlertDialogBuilder(this)
                .setTitle(R.string.auth_error)
                .setMessage(R.string.auth_error_tip)
                .setPositiveButton(R.string.ok, null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Intent intent = new Intent(AuthErrorActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra(MainActivity.KEY_BACK_TO_ENTRY, true);
                        startActivity(intent);
                    }

                })
                .show();
    }

}
