package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.lntu.online.R;

import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.ui.listener.NavigationFinishClickListener;
import org.lntu.online.util.DocumentUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LicenseActivity extends BaseActivity {

    @InjectView(R.id.license_toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.license_tv_license)
    protected TextView tvLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        ButterKnife.inject(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        tvLicense.setText(DocumentUtils.getString(this, R.raw.open_source));
    }
    
}
