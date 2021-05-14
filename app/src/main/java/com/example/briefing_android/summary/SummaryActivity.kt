package com.example.briefing_android.summary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.briefing_android.R
import com.example.briefing_android.main.MainActivity
import com.example.briefing_android.summary.recyclerview_comment.Graph_Viewpager_adapter
import com.google.android.material.tabs.TabLayout


class SummaryActivity : AppCompatActivity() {
    private lateinit var btn_back: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)


        var indicator1 : ImageView = findViewById(R.id.indicator1)
        var indicator2 : ImageView = findViewById(R.id.indicator2)


        var comment_viewPager = findViewById<ViewPager>(R.id.comment_viewPager)
        var comment_tablayout = findViewById<TabLayout>(R.id.comment_tablayout)

        //그래프 뷰페이저
//        var viewpager = findViewById<ViewPager>(R.id.viewpager)
//        var viewpagerAdapter = ViewPagerAdapter(this)
//        viewpager.adapter=viewpagerAdapter

        val graph_viewPager = findViewById<ViewPager>(R.id.viewpager)
        val graph_fragmentAdapter = Graph_Viewpager_adapter(supportFragmentManager)
        graph_viewPager.adapter = graph_fragmentAdapter


        graph_viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                indicator1.setImageDrawable(getDrawable(R.drawable.shape_circle1))
                indicator2.setImageDrawable(getDrawable(R.drawable.shape_circle1))

                when(position){
                    0-> indicator1.setImageDrawable(getDrawable(R.drawable.shape_circle2))
                    1-> indicator2.setImageDrawable(getDrawable(R.drawable.shape_circle2))
                }
            }
        })

       //댓글창 프래그먼트 뷰페이저
        val commnt_fragmentAdapter = Comment_Viewpager_adapter(supportFragmentManager)
        comment_viewPager.adapter = commnt_fragmentAdapter
        comment_tablayout.setupWithViewPager(comment_viewPager)

        btn_back = findViewById(R.id.btn_back)
        // 1. 뒤로가기 버튼 이벤트
        btn_back.setOnClickListener {
            Log.d("11","11111")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Log.d("22","22222")
        }


    }
}