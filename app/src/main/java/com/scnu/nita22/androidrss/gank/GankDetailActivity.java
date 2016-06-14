package com.scnu.nita22.androidrss.gank;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.scnu.nita22.androidrss.R;

public class GankDetailActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    private CircleProgressBar mCircleProgressBar;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.detail_container);
        mCircleProgressBar = (CircleProgressBar) findViewById(R.id.detail_progressBar);
        mCircleProgressBar.setShowArrow(true);
        mCircleProgressBar.setCircleBackgroundEnabled(true);
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
                    mCircleProgressBar.setProgress(newProgress);
                    if (newProgress == 100) {
                        mCircleProgressBar.setVisibility(View.GONE);
                        Snackbar.make(mRelativeLayout, R.string.finished, Snackbar.LENGTH_SHORT).show();
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
