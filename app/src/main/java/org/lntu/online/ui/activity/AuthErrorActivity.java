package org.lntu.online.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;

import org.lntu.online.R;
import org.lntu.online.ui.base.FullLayoutActivity;

public class AuthErrorActivity extends FullLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MaterialDialog.Builder(this)
                .backgroundColorRes(android.R.color.white)
                .title(R.string.auth_error)
                .content(R.string.auth_error_tip)
                .positiveText(R.string.ok)
                .positiveColorRes(R.color.color_accent)
                .dismissListener(new DialogInterface.OnDismissListener() {

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
