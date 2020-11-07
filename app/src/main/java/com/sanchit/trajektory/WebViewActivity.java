package com.sanchit.trajektory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String Url = intent.getExtras().getString("Url");

        WebView webView = (WebView) findViewById(R.id.webView10);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar10);

        progressBar.setVisibility(View.VISIBLE);
        //String finalURL = "https://docs.google.com/gview?embedded=true&url="+Url;

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                //getSupportActionBar().setTitle("Loading...");
                if(newProgress==100)
                {progressBar.setVisibility(View.GONE);
                    // getSupportActionBar().setTitle(R.string.app_name);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(Url);

    }
}