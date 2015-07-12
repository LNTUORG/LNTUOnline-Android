package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import org.lntu.online.R;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.ui.listener.NavigationFinishClickListener;
import org.lntu.online.util.ShipUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SenateNoticeDetailActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {

    @InjectView(R.id.senate_notice_detail_toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.senate_notice_detail_web_view)
    protected WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senate_notice_detail);
        ButterKnife.inject(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.senate_notice);
        toolbar.setOnMenuItemClickListener(this);

        webView.loadUrl(getIntent().getStringExtra("url"));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_browser:
                ShipUtils.openUrlByBrowser(this, getIntent().getStringExtra("url"));
                return true;
            case R.id.action_refresh:
                webView.loadUrl(webView.getUrl());
                return true;
            default:
                return false;
        }
    }

}
