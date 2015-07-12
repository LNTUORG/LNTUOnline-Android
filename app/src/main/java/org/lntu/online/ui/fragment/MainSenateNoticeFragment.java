package org.lntu.online.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.lntu.online.R;
import org.lntu.online.ui.activity.MainActivity;
import org.lntu.online.ui.activity.SenateNoticeDetailActivity;
import org.lntu.online.util.ShipUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainSenateNoticeFragment extends MainActivity.BaseFragment implements Toolbar.OnMenuItemClickListener {

    private static final String url = "http://60.18.131.133:8090/lntu/pub_message/messagesplitepageopenwindow.jsp?fmodulecode=5100&modulecode=5100&messagefid=5100";

    @InjectView(R.id.main_senate_notice_toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.main_senate_notice_web_view)
    protected WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_senate_notice, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        toolbar.setNavigationOnClickListener(getOpenNavigationClickListener());
        toolbar.inflateMenu(R.menu.senate_notice);
        toolbar.setOnMenuItemClickListener(this);

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(getActivity(), SenateNoticeDetailActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
                return true;
            }

        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_browser:
                ShipUtils.openUrlByBrowser(getActivity(), url);
                return true;
            case R.id.action_refresh:
                webView.loadUrl(url);
                return true;
            default:
                return false;
        }
    }

}