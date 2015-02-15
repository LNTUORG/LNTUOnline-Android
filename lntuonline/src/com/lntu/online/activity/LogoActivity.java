package com.lntu.online.activity;

import com.lntu.online.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LogoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch(InterruptedException e) {
                }
                if (!isFinishing()) {
                    Intent intent = new Intent(LogoActivity.this, LoginActivity.class);
                    intent.putExtra("autoLogin", true);
                    startActivity(intent);
                    finish();
                }
            }

        }.start();
    }

}
