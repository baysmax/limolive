package com.example.project.limolive.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.project.limolive.R;

/**
 * Created by AAA on 2017/9/1.
 */

public class LoadHtmlActivity extends BaseActivity{
    private WebView localWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_html);
        loadTitle();
        localWebview = (WebView) findViewById(R.id.local_webview);
        WebSettings settings = localWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        localWebview.loadUrl("file:///android_asset/UserProtocol.html");
        localWebview.setWebViewClient(new WebViewClient());
    }

    private void loadTitle() {
        setTitleString("开店须知");
        setLeftImage(R.mipmap.icon_return);
        //setRightImage(R.mipmap.fenlei);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadHtmlActivity.this.finish();
            }
        });
    }

}
