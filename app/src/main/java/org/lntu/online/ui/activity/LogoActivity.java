package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.lntu.online.R;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.base.BaseActivity;

import java.util.Random;

public class LogoActivity extends BaseActivity implements Runnable {

    private static final int LOGO_LAYOUTS[] = {
            R.layout.activity_logo_0,
            R.layout.activity_logo_1,
            R.layout.activity_logo_2,
            R.layout.activity_logo_3,
            R.layout.activity_logo_4,
            R.layout.activity_logo_5,
            R.layout.activity_logo_6,
            R.layout.activity_logo_7,
            R.layout.activity_logo_8,
            R.layout.activity_logo_9
    };

    private static final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOGO_LAYOUTS[new Random().nextInt(LOGO_LAYOUTS.length)]);
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
