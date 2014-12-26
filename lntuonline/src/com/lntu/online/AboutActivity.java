package com.lntu.online;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lntu.online.info.AppInfo;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView tvVersionName= (TextView) findViewById(R.id.about_tv_version_name);
        tvVersionName.setText("v" + AppInfo.getVersionName() + "-build-" + AppInfo.getVersionCode());
    }

    public void onActionBarBtnLeft(View view) {
        finish();
    }

    public void onBtnThank(View view) {
        startActivity(new Intent(this, ThankActivity.class));
    }

}
