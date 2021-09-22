package com.example.briefing_android.summary.comment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class Comment_Viewpager_adapter(fm: FragmentManager,url:String) : FragmentStatePagerAdapter(fm)  {
    val url =url

//    var frag1 : Fragment = fragment_korean(url)
//    var frag2 : Fragment = fragment_english(url)
//    var frag3 : Fragment = fragment_etc(url)
//    var fragments = listOf<Fragment>(frag1,frag2,frag3)

    override fun getItem(position: Int) :Fragment{
        return when (position){
            0 -> {
                Log.v("한국어 fragment", "호출")
                fragment_korean(url)
            }
            1 -> {
                Log.v("영어 fragment", "호출")
                fragment_english(url)
            }
            2-> {
                Log.v("그외 fragment", "호출")
                fragment_etc(url)
            }
            else -> throw IllegalStateException("Unexpected position $position")
        }
        //return fragments.get(position)
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