package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.ui.base.StatusBarActivity;
import org.lntu.online.ui.listener.NavigationFinishClickListener;
import org.lntu.online.util.DocumentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LicenseActivity extends StatusBarActivity {

    @Bind(R.id.license_toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.license_tv_license)
    protected TextView tvLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        tvLicense.setText(DocumentUtils.getString(this, R.raw.open_source));
    }

}
