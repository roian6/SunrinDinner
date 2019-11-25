package com.example.foodrandom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FoodActivity extends AppCompatActivity {

    private WebView web;
    private WebSettings webSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Intent getIntent = getIntent();

        Toolbar toolbar = findViewById(R.id.food_toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle(getIntent.getStringExtra("name"));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        web = findViewById(R.id.webview);
        web.setWebViewClient(new WebViewClient());
        webSet = web.getSettings();
        webSet.setJavaScriptEnabled(true);

        web.loadUrl(getIntent.getStringExtra("link"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
