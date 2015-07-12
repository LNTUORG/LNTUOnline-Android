package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.lntu.online.R;

import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.ui.listener.NavigationFinishClickListener;
import org.lntu.online.util.AppUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NotificationActivity extends BaseActivity {

    @InjectView(R.id.notification_toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.inject(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
    }

}
