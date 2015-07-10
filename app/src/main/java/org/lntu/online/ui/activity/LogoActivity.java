package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.shared.LoginShared;
import org.lntu.online.ui.base.BaseActivity;

import java.util.Random;

public class LogoActivity extends BaseActivity implements Runnable {

    private static final Handler handler = new Handler();

    private static int[] bgIds = {
            R.drawable.logo_bg_0,
            R.drawable.logo_bg_1,
            R.drawable.logo_bg_2,
            R.drawable.logo_bg_3,
            R.drawable.logo_bg_4,
            R.drawable.logo_bg_5,
            R.drawable.logo_bg_6,
            R.drawable.logo_bg_7
    };

    private static int[] colors = {
            0xccffffff,
            0xccffffff,
            0xff000000,
            0xff000000,
            0xccffffff,
            0xccffffff,
            0xccffffff,
            0xccffffff
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int n = new Random().nextInt(bgIds.length + 1);
        if (n == bgIds.length) {
            setContentView(R.layout.activity_logo_0);
        } else {
            setContentView(R.layout.activity_logo_1);
            ImageView imgBg = (ImageView) findViewById(R.id.logo_img_bg);
            TextView tvCopyright = (TextView) findViewById(R.id.logo_tv_copyright);
            imgBg.setImageResource(bgIds[n]);
            tvCopyright.setTextColor(colors[n]);
        }
        handler.postDelayed(this, 1500);
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
