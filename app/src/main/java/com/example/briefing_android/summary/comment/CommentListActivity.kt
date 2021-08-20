package com.example.briefing_android.summary.comment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.briefing_android.R
import com.example.briefing_android.api.CommentURLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.sign.SignUpPwActivity
import com.example.briefing_android.summary.Comment_Viewpager_adapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.tabs.TabLayout

class CommentListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        //댓글창 프래그먼트 뷰페이저
        var url = intent.getStringExtra("url")
        val comment_fragmentAdapter = Comment_Viewpager_adapter(supportFragmentManager, "https://www.youtube.com/watch?v=vQJJ255QFGM")
        var comment_viewPager = findViewById<ViewPager>(R.id.comment_viewPager)
        var comment_tablayout = findViewById<TabLayout>(R.id.comment_tablayout)
        comment_viewPager.adapter = comment_fragmentAdapter
        comment_tablayout.setupWithViewPager(comment_viewPager)

        var backbutton: ImageButton = findViewById(R.id.backbutton)
        backbutton.setOnClickListener { // 뒤로가기
            finish()
        }

        var btn_graph: Button = findViewById(R.id.btn_graph)
        btn_graph.setOnClickListener {
            // 댓글분석-파이차트 확인버튼
            val intent = Intent(this, CommentGraphActivity::class.java)
            startActivity(intent)
        }

    }
}