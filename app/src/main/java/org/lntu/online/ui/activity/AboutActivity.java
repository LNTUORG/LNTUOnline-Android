package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.lntu.online.R;

import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.util.AppUtils;
import org.lntu.online.util.ShipUtils;
import org.lntu.online.util.UpdateUtils;

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
        getSupportActionBar().setTitle("");

        tvVersionName.setText("当前版本：" + AppUtils.getVersionName(this) + "-build-" + AppUtils.getVersionCode(this));
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

    @OnClick(R.id.about_btn_version)
    protected void onBtnVersionClick() {
        UpdateUtils.forceUpdate(this);
    }

    @OnClick(R.id.about_btn_homepage)
    protected void onBtnHomepageClick() {
        ShipUtils.homepage(this);
    }

    @OnClick(R.id.about_btn_term_of_service)
    protected void onBtnTermsOfServiceClick() {
        startActivity(new Intent(this, TermsOfServiceActivity.class));
    }

    @OnClick(R.id.about_btn_faq)
    protected void onBtnFaqClick() {
        startActivity(new Intent(this, FaqActivity.class));
    }

    @OnClick(R.id.about_btn_open_source_license)
    protected void onBtnOpenSourceLicenseClick() {
        startActivity(new Intent(this, LicenseActivity.class));
    }

}
