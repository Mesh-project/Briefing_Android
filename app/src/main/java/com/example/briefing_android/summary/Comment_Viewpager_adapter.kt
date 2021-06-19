package com.example.briefing_android.summary

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class Comment_Viewpager_adapter(fm: FragmentManager,url:String) : FragmentStatePagerAdapter(fm)  {
    val url =url
    override fun getItem(position: Int) :Fragment{

        return when (position) {
            0 -> {fragment_korean(url)}
            1 -> {fragment_english(url)}
            else->{fragment_etc(url)}
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "한국어"
            1 -> "영어"
            else->"etc."
        }

    }
}