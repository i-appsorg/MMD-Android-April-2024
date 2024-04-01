package com.MamaDevalayam.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.MamaDevalayam.CommonActivity.CommonBackActivity;
import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.R;

public class AboutNewActivity extends CommonBackActivity {
    private String TAG = "AboutActivity";
    Toolbar toolbar;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_about_new,TAG);
        setTitle("");
//        toolbar = findViewById(R.id.commonMenuActivityToolbar);
        init();
    }

    @SuppressLint("JavascriptInterface")
    private void init() {
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(ApiClient.About_URL);
        webView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webView.getSettings();
        webSettings.setTextZoom(webSettings.getTextZoom() - 40);
        ((TextView)findViewById(R.id.tvDummy)).setMovementMethod(new ScrollingMovementMethod());
       /* webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings();
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setScrollbarFadingEnabled(true);
        WebSettings webSettings = webView.getSettings();

        webSettings.setTextZoom(webSettings.getTextZoom() - 40);*/



    }


}
