package com.lntu.online.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lntu.online.R;
import com.lntu.online.ui.base.BaseActivity;
import com.lntu.online.util.AppUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.about_tv_version_name)
    protected TextView tvVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvVersionName.setText("v" + AppUtils.getVersionName(this) + "-build-" + AppUtils.getVersionCode(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.about_btn_home_page)
    public void onBtnHomePage(View view) {
        TextView tv = (TextView) view;
        Uri uri = Uri.parse(tv.getText().toString() + "");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
