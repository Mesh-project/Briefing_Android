package com.example.briefing_android.summary

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.briefing_android.R
import com.example.briefing_android.main.MainActivity
import org.w3c.dom.Text

class body_text: AppCompatActivity() {
    private lateinit var btn_back: ImageButton
    private lateinit var tv_body :TextView
    private lateinit var tv_title :TextView
    private lateinit var tv_channelname :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.body_text)

        val script = intent.getStringExtra("script")
        val title = intent.getStringExtra("tv_title")
        val channelname = intent.getStringExtra("tv_channelname")

        tv_body = findViewById(R.id.tv_body)
        tv_title = findViewById(R.id.tv_title)
        tv_channelname = findViewById(R.id.tv_channelname)

        tv_body.setText(script)
        tv_title.setText(title)
        tv_channelname.setText(channelname)



        btn_back = findViewById(R.id.btn_back)
        // 1. 뒤로가기 버튼 이벤트
        btn_back.setOnClickListener {
           finish()
        }
    }
}