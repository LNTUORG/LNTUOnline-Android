package org.lntu.online.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.ui.listener.NavigationFinishClickListener;
import org.lntu.online.util.AppUtils;
import org.lntu.online.util.ShipUtils;
import org.lntu.online.util.UpdateUtils;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @Bind(R.id.about_toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.about_layout_collapsing_toolbar)
    protected CollapsingToolbarLayout layoutCollapsingToolbar;

    @Bind(R.id.about_tv_version)
    protected TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        layoutCollapsingToolbar.setTitle(getString(R.string.about));

        tvVersion.setText("当前版本：" + AppUtils.getVersionName(this) + "-build-" + AppUtils.getVersionCode(this));
    }

    @OnClick(R.id.about_btn_version)
    protected void onBtnVersionClick() {
        UpdateUtils.forceUpdate(this);
    }

    @OnClick(R.id.about_btn_homepage)
    protected void onBtnHomepageClick() {
        ShipUtils.homepage(this);
    }

    @OnClick(R.id.about_btn_open_source)
    protected void onBtnOpenSourceClick() {
        ShipUtils.openSource(this);
    }

    @OnClick(R.id.about_btn_online)
    protected void onBtnOnlineClick() {
        ShipUtils.webOnline(this);
    }

    @OnClick(R.id.about_btn_photo)
    protected void onBtnPhotoClick() {
        ShipUtils.photoOnline(this);
    }

    @OnClick(R.id.about_btn_grade_in_play)
    protected void onBtnGradeInPlayClick() {
        ShipUtils.appStore(this);
    }

    @OnClick(R.id.about_btn_share_to_friends)
    protected void onBtnShareToFriendsClick() {
        ShipUtils.share(this);
    }

    @OnClick(R.id.about_btn_feedback)
    protected void onBtnFeedbackClick() {
        startActivity(new Intent(this, AdviceActivity.class));
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
