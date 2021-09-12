package com.example.briefing_android.summary.comment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.briefing_android.R
import com.google.android.material.tabs.TabLayout

class CommentListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        //댓글창 프래그먼트 뷰페이저
        //var url = intent.getStringExtra("url")
        var url = "sWuerIFLjjk"
        val comment_fragmentAdapter = Comment_Viewpager_adapter(supportFragmentManager, url)
        var comment_viewPager = findViewById<ViewPager>(R.id.comment_viewPager)
        var comment_tablayout = findViewById<TabLayout>(R.id.comment_tablayout)
        comment_viewPager.adapter = comment_fragmentAdapter
        comment_tablayout.setupWithViewPager(comment_viewPager)

        var backbutton: ImageButton = findViewById(R.id.backbutton)
        backbutton.setOnClickListener { // 뒤로가기 버튼
            finish()
        }

        var btn_graph: Button = findViewById(R.id.btn_graph)
        btn_graph.setOnClickListener {
            // 파이차트 분석으로 넘어가기
            val intent = Intent(this, CommentGraphActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }

    }
}