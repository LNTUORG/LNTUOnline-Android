package org.lntu.online.display.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.lntu.online.R;
import org.lntu.online.display.base.StatusBarActivity;
import org.lntu.online.display.listener.NavigationFinishClickListener;
import org.lntu.online.display.util.ToastUtils;
import org.lntu.online.util.ResUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LicenseActivity extends StatusBarActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.tv_license)
    protected TextView tvLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        try {
            tvLicense.setText(ResUtils.getRawString(this, R.raw.open_source));
        } catch (IOException e) {
            tvLicense.setText(null);
            ToastUtils.with(this).show("资源读取失败");
        }
    }

}
