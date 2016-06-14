package com.scnu.nita22.androidrss.Activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.scnu.nita22.androidrss.R;

public class DetailActivity extends AppCompatActivity {

    private CoordinatorLayout mCoordinatorLayout;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.detail_container);
        mWebView = (WebView) findViewById(R.id.detail_webview);

        initWebview();
        String webUrl = getIntent().getStringExtra("webUrl");
        mWebView.loadUrl(webUrl);
    }

    private void initWebview() {
        WebSettings webSettings = null;
        if (mWebView != null) {
            webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            webSettings.setSupportZoom(true);
            webSettings.setSupportMultipleWindows(true);
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    if (newProgress == 100) {
                        Snackbar.make(mCoordinatorLayout, R.string.finished, Snackbar.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView = null;
    }
}
