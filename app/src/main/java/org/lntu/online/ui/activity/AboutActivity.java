package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.umeng.update.UmengUpdateAgent;

import org.lntu.online.BuildConfig;
import org.lntu.online.R;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.ui.base.StatusBarActivity;
import org.lntu.online.ui.listener.NavigationFinishClickListener;
import org.lntu.online.util.ShipUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends StatusBarActivity {

    public static final String VERSION_TEXT = BuildConfig.VERSION_NAME + "-build-" + BuildConfig.VERSION_CODE;

    @Bind(R.id.about_toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.about_tv_version_name)
    protected TextView tvVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        tvVersionName.setText("当前版本：" + VERSION_TEXT);
    }

    @OnClick(R.id.about_btn_version)
    protected void onBtnVersionClick() {
        UmengUpdateAgent.forceUpdate(this);
    }

    @OnClick(R.id.about_btn_app_homepage)
    protected void onBtnAppHomepageClick() {
        ShipUtils.openInBrowser(this, getString(R.string.app_homepage_content));
    }

    @OnClick(R.id.about_btn_lntu_online_homepage)
    protected void onBtnLntuOnlineHomepageClick() {
        ShipUtils.openInBrowser(this, getString(R.string.lntu_online_homepage_content));
    }

    @OnClick(R.id.about_btn_open_source_url)
    protected void onBtnOpenSourceUrlClick() {
        ShipUtils.openInBrowser(this, getString(R.string.open_source_url_content));
    }

    @OnClick(R.id.about_btn_about_author)
    protected void onBtnAboutAuthorClick() {
        ShipUtils.openInBrowser(this, getString(R.string.about_author_content));
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
