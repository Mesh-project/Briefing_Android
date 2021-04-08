package com.example.briefing_android.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R
import com.example.briefing_android.main.recyclerview_comment.CommentItem
import com.example.briefing_android.main.recyclerview_comment.rv_Adapter

class fragment_etc:Fragment() {
    private lateinit var  FErecyclerview : RecyclerView
    private var mpadapter3: rv_Adapter = rv_Adapter(R.layout.comment_item)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var etc_listview = inflater.inflate(R.layout.etc_list, container, false)
        var thiscontext = container!!.getContext()
        FErecyclerview = etc_listview.findViewById(R.id.etc_recyclerview)

        var mp_datalist = ArrayList<ArrayList<CommentItem>>()

        var commentList = arrayListOf<CommentItem>(
                CommentItem("kim","2020.01.01","喜欢~",1),
                CommentItem("yeo","2020.01.02","好きだ~",37),
                CommentItem("kang","2020.03.01","ser aficionado~",10000),
                CommentItem("na","2020.12.22","repugnar~",777),
                CommentItem("oh","2020.03.11","Je déteste.~",3),
                CommentItem("dong","2020.07.23","嫌う~",43),
        )

        //리사이클러뷰의 어댑터 세팅
        FErecyclerview.adapter=mpadapter3

        //리사이클러뷰 배치
        val lm = LinearLayoutManager(thiscontext)
        FErecyclerview.layoutManager=lm

        mpadapter3.data=commentList
        mp_datalist.add(mpadapter3.data)

        mpadapter3.notifyDataSetChanged()


        return etc_listview

    }
}