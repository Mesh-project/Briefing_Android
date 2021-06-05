package com.example.briefing_android.summary.recyclerview_comment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.briefing_android.summary.fragment_datamining
import com.example.briefing_android.summary.fragment_piechart

class Graph_Viewpager_adapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int) : Fragment {
        return when (position) {
            0 -> {fragment_piechart()}
            else->{return fragment_datamining()}
        }
    }
    override fun getCount(): Int {
        return 2
    }

}