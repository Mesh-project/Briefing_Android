package com.example.briefing_android.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.briefing_android.R

class fragment_datamining : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var datamining_layout = inflater.inflate(R.layout.fragment_dataminig, container, false)
        var thiscontext = container!!.getContext()

        return datamining_layout
    }
}