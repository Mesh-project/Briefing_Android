package com.example.briefing_android.summary

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class Comment_Viewpager_adapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm)  {

    override fun getItem(position: Int) :Fragment{
        return when (position) {
            0 -> {fragment_korean()}
            1 -> {fragment_english()}
            else->{return fragment_etc()}
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "한국어"
            1 -> "영어"
            else->{return "etc."}
        }

    }
}