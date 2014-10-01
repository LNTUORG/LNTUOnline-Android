package com.lntu.online;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NoticeActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        webView = (WebView) findViewById(R.id.notice_wv);
        webView.loadUrl("http://60.18.131.131/lntu//pub_message/messagesplitepageopenwindow.jsp?fmodulecode=5100&modulecode=5100&messagefid=5100");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        
        // TODO
        Toast.makeText(this, "该板块教务在线做的太烂，开发者也无能为力了。 ╮(╯▽╰)╭", Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void onActionBarBtnLeft(View view) {
    	finish();
    }

}
