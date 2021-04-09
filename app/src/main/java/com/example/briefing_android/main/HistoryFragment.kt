package com.example.briefing_android.main

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R

class HistoryFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var rv_adapter : ht_Adapter
    private val repository = historyData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slide_page_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var context = view.getContext()
        rv = view.findViewById(R.id.history_recyclerView)
        rv_adapter = ht_Adapter(context)
        rv.adapter=rv_adapter

        //rv.layoutManager = LinearLayoutManager(context)
        val gridLayoutManager = GridLayoutManager(context,2)
        rv.layoutManager = gridLayoutManager
        rv_adapter.data = repository.getRepoList()

    }
}