package com.example.games;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class WebArticleExm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_article_exm);
        WebView webView=(WebView)findViewById(R.id.webview);
        webView.loadUrl("https://fee.org/articles/call-of-duty-modern-warfare-is-the-most-popular-game-of-2019-here-s-why/");

    }
}
