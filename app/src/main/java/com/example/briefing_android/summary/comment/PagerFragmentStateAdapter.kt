package com.example.briefing_android.summary.comment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerFragmentStateAdapter(fragmentActivity: FragmentActivity,url:String): FragmentStateAdapter(fragmentActivity) {
    //var fragments : ArrayList<Fragment> = ArrayList()
    val url =url

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                Log.v("한국어 fragment", "호출")
                fragment_korean(url)
            }
            else -> {
                Log.v("외국어 fragment", "호출")
                fragment_foreign(url)
            }
        }
    }


}