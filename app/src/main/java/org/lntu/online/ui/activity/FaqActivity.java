package org.lntu.online.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.lntu.online.R;
import org.lntu.online.ui.base.StatusBarActivity;
import org.lntu.online.ui.listener.NavigationFinishClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FaqActivity extends StatusBarActivity {

    @Bind(R.id.faq_toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.faq_web_view)
    protected WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        webView.loadUrl("http://takwolf.com/lntuonline/faq");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
    }

}
