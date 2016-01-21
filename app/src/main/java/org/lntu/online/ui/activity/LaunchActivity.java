package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.umeng.update.UmengUpdateAgent;

import org.lntu.online.R;
import org.lntu.online.storage.LoginShared;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.ui.base.FullLayoutActivity;
import org.lntu.online.util.HandlerUtils;

public class LaunchActivity extends FullLayoutActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        HandlerUtils.postDelayed(this, 2000);
    }

    @Override
    public void run() {
        if (!isFinishing()) {
            if (TextUtils.isEmpty(LoginShared.getLoginToken(this)) || !LoginShared.isHoldOnline(this)) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, MainActivity.class));
            }
            UmengUpdateAgent.update(this); // 友盟更新
            finish();
        }
    }

}
