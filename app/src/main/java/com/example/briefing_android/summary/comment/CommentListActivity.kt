package com.example.briefing_android.summary.comment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.briefing_android.R
import com.example.briefing_android.api.CommentURLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.summary.recyclerview_comment.CommentItem
import com.example.briefing_android.summary.recyclerview_comment.rv_Adapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CommentListActivity : AppCompatActivity() {
    var url = "sWuerIFLjjk"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)


        var tab_text : List<String> = listOf("한국어","영어","그 외")

        //댓글창 프래그먼트 뷰페이저
        //var url = intent.getStringExtra("url")

        //val comment_fragmentAdapter = Comment_Viewpager_adapter(supportFragmentManager, url)
        var comment_viewPager = findViewById<ViewPager2>(R.id.comment_viewPager)
        var comment_tablayout = findViewById<TabLayout>(R.id.comment_tablayout)
        val pagerAdapter = PagerFragmentStateAdapter(this, url)

        comment_viewPager.adapter = pagerAdapter
        // TabLayout attach
        TabLayoutMediator(comment_tablayout, comment_viewPager) { tab, position ->
            tab.text = tab_text[position]
        }.attach()



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