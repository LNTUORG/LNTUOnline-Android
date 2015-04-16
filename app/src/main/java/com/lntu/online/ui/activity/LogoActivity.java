package com.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.lntu.online.R;
import com.lntu.online.shared.LoginShared;
import com.lntu.online.ui.base.BaseActivity;

public class LogoActivity extends BaseActivity implements Runnable {

    private final static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        handler.postDelayed(this, 3000);
    }

    @Override
    public void run() {
        if (!isFinishing()) {
            if (TextUtils.isEmpty(LoginShared.getLoginToken(this)) || !LoginShared.isHoldOnline(this)) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
            finish();
        }
    }

}
