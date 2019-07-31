package com.dailynews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsDetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsdetailed);
        TextView title = findViewById(R.id.news_detailed_title);
        TextView image_source = findViewById(R.id.news_detailed_image_source);
        ImageView imageView = findViewById(R.id.news_detailed_image);
        WebView webView = findViewById(R.id.webView);
        String stitle = getIntent().getStringExtra("title");
        String data = getIntent().getStringExtra("body");
        String image = getIntent().getStringExtra("image");
        String simage_source = getIntent().getStringExtra("image_source");
        title.setText(stitle);
        image_source.setText(simage_source);
        Glide.with(this).load(image).into(imageView);
        webView.loadData(data,"text/html","utf-8");
    }
}
