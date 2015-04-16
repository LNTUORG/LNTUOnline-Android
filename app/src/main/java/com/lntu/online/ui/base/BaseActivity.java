package com.lntu.online.ui.base;

import android.support.v7.app.ActionBarActivity;

import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

}
