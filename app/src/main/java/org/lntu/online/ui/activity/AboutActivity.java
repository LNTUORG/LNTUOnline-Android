package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.umeng.update.UmengUpdateAgent;

import org.lntu.online.BuildConfig;
import org.lntu.online.R;
import org.lntu.online.util.ShipUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    public static final String VERSION_TEXT = BuildConfig.VERSION_NAME + "-build-" + BuildConfig.VERSION_CODE;

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.about_tv_version_name)
    protected TextView tvVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        tvVersionName.setText("当前版本：" + VERSION_TEXT);
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
        UmengUpdateAgent.forceUpdate(this);
    }

    @OnClick(R.id.about_btn_homepage)
    protected void onBtnHomepageClick() {
        ShipUtils.openInBrowser(this, getString(R.string.official_homepage_content));
    }

    @OnClick(R.id.about_btn_online)
    protected void onBtnOnlineClick() {
        ShipUtils.openInBrowser(this, "http://60.18.131.131:11180/academic/index.html");
    }

    @OnClick(R.id.about_btn_term_of_service)
    protected void onBtnTermsOfServiceClick() {
        startActivity(new Intent(this, TermsOfServiceActivity.class));
    }

    @OnClick(R.id.about_btn_open_source_license)
    protected void onBtnOpenSourceLicenseClick() {
        startActivity(new Intent(this, LicenseActivity.class));
    }

}
