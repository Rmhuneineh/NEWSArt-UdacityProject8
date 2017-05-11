package com.example.android.newsart;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by rmhuneineh on 11/05/2017.
 */

public class ArticleWebViewClient extends WebViewClient {
    private ProgressBar mProgressBar;

    public ArticleWebViewClient(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mProgressBar.setVisibility(View.GONE);
    }
}
