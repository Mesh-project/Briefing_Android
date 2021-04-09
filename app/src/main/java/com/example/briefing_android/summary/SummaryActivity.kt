package com.example.briefing_android.summary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.example.briefing_android.R
import com.google.android.material.tabs.TabLayout

class SummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)


        var indicator1 : ImageView = findViewById(R.id.indicator1)
        var indicator2 : ImageView = findViewById(R.id.indicator2)


        var comment_viewPager = findViewById<ViewPager>(R.id.comment_viewPager)
        var comment_tablayout = findViewById<TabLayout>(R.id.comment_tablayout)

        //그래프 뷰페이저
        var viewpager = findViewById<ViewPager>(R.id.viewpager)
        var viewpagerAdapter = ViewPagerAdapter(this)
        viewpager.adapter=viewpagerAdapter

        viewpager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
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



    }
}