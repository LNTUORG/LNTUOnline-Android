package org.lntu.online.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;

import org.lntu.online.R;

public class AuthErrorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MaterialDialog.Builder(this)
                .backgroundColorRes(android.R.color.white)
                .title(R.string.auth_error)
                .titleColorRes(R.color.text_color_primary)
                .content(R.string.auth_error_tip)
                .contentColorRes(R.color.text_color_secondary)
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
