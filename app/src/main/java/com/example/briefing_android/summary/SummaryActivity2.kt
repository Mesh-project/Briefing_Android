package com.example.briefing_android.summary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.briefing_android.R
import com.example.briefing_android.main.MainActivity

class SummaryActivity2: AppCompatActivity() {
    private lateinit var btn_back: ImageButton
    private lateinit var btn_content: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary2)


        btn_back = findViewById(R.id.btn_back)
        // 1. 뒤로가기 버튼 이벤트
        btn_back.setOnClickListener {
            finish()
        }

        btn_content=findViewById(R.id.btn_content)
        //2. 본문 보기 버튼
        btn_content.setOnClickListener {
            var intent = Intent(this, body_text::class.java)
            startActivity(intent)
        }
    }
}