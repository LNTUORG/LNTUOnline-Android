package com.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.lntu.online.R;
import com.lntu.online.ui.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthErrorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_error);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.auth_error_btn_close)
    protected void onBtnCloseClick() {
        finish();
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(MainActivity.KEY_BACK_TO_ENTRY, true);
        startActivity(intent);
        super.onDestroy();
    }

}
