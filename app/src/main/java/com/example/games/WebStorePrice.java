package com.example.games;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebStorePrice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_store_price);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebView store=findViewById(R.id.store_web);
        store.setWebViewClient(new WebViewClient());
        store.getSettings().setJavaScriptEnabled(true);
        store.getSettings().setDomStorageEnabled(true);
        store.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        store.loadUrl(getIntent().getStringExtra("url"));
    }
}
