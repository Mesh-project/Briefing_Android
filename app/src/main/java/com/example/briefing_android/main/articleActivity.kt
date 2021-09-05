package com.example.briefing_android.main

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.briefing_android.R

class articleActivity:AppCompatActivity() {
    private lateinit var btn_back: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val myWebView: WebView = findViewById(R.id.article_webview)

        val keyword1: String = intent.getStringExtra("top1")
        val keyword2: String = intent.getStringExtra("top2")
        val keyword3: String = intent.getStringExtra("top3")

        val url1: String = "https://search.naver.com/search.naver?query="
        val url2: String = "&where=news&ie=utf8&sm=nws_hty"
        myWebView.loadUrl(url1 + keyword1 + "+" + keyword2 + "+" + keyword3 + url2)
        Log.d("기사 주소", url1 + keyword1 + "+" + keyword2 + "+" + keyword3 + url2)



        btn_back = findViewById(R.id.btn_back)
        // 1. 뒤로가기 버튼 이벤트
        btn_back.setOnClickListener {
            finish()
        }



    }
}