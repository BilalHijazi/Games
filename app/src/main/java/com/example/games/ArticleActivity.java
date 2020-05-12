package com.example.games;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleActivity.this,MainActivity.class));
            }
        });

        WebView article=findViewById(R.id.article_web);
        article.setWebViewClient(new WebViewClient());
        article.getSettings().setJavaScriptEnabled(true);
        article.getSettings().setDomStorageEnabled(true);
        article.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        article.loadUrl(getIntent().getStringExtra("url"));








    }
}
