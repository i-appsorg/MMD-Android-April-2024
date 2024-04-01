package com.MamaDevalayam.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.widget.Toolbar;

import com.MamaDevalayam.CommonActivity.CommonBackActivity;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.R;

public class HelpSupportActivity extends CommonBackActivity {

    private final String TAG = "HelpSupportActivity";
    Toolbar toolbar;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_help_support, TAG);
        setTitle("");
        toolbar = findViewById(R.id.commonMenuActivityToolbar);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings();
        webView.setScrollbarFadingEnabled(true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadUrl(ApiClient.Help_URL);

        final WebSettings webSettings = webView.getSettings();
//        webSettings.setTextZoom(webSettings.getTextZoom() - 68);


    }
}
