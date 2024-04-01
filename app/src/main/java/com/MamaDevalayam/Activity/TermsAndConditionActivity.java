package com.MamaDevalayam.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.MamaDevalayam.RetrofitAPI.ApiClient;
import com.MamaDevalayam.R;

public class TermsAndConditionActivity extends AppCompatActivity {

    WebView webView;
    ImageView back_icon_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        init();
        listener();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.webView);
        back_icon_img = (ImageView) findViewById(R.id.back_icon_img);
        webView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webView.getSettings();
        webSettings.setTextZoom(webSettings.getTextZoom() - 40);
        webView.loadUrl(ApiClient.TC_URL);


       /* webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings();
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setScrollbarFadingEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setTextZoom(webSettings.getTextZoom() - 40);*/
    }

    private void listener() {
        back_icon_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}