package com.example.childapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_food_tips.*


class FoodTips : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_tips)

        val myWebView2: WebView = findViewById(R.id.webview2)
        myWebView2.settings.javaScriptEnabled = true

        webview2.loadUrl("https://www.babycenter.com/0_age-by-age-guide-to-feeding-your-baby_1400680.bc#articlesection1")

    }


}
