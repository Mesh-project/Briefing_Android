package com.example.briefing_android.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.briefing_android.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

private const val NUM_PAGES = 2 // 페이지 수를 정해둠

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // 콘텐츠 뷰를 ViewPager2가 있는 레이아웃응로 설정

        // ViewPager2 및 PagerAdapter를 인스턴스화합니다.
        viewPager = findViewById(R.id.viewPager_main)

        // 뷰 페이저 위젯에 페이지를 제공하는 페이저 어댑터.
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        //viewPager_main.adapter = ScreenSlidePagerAdapter(this)
        //viewPager_main.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val wormDotsIndicator = findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
        wormDotsIndicator.setViewPager2(viewPager)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // 사용자가 첫 번째 페이지에서 뒤로가기 버튼을 누르면 finish 하도록 하고
            super.onBackPressed()
        } else {
            // 그렇지 않으면 이전 페이지로 이동하게 한다.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    // ViewPagerAdapter
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES // 페이지 수 리턴

        override fun createFragment(position: Int): Fragment {
            return when(position){ // 페이지 포지션에 따라 그에 맞는 프래그먼트를 보여줌
                0 -> MainFragment()
                else -> HistoryFragment()
            }
        }
    }
}