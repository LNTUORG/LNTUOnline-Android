package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.lntu.online.R;
import org.lntu.online.ui.base.BaseActivity;
import org.lntu.online.ui.listener.NavigationFinishClickListener;

import butterknife.ButterKnife;
import butterknife.Bind;

public class TermsOfServiceActivity extends BaseActivity {

    @Bind(R.id.terms_of_service_toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.terms_of_service_web_view)
    protected WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        webView.loadUrl("http://takwolf.com/lntuonline/terms-of-service");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

    }

}
