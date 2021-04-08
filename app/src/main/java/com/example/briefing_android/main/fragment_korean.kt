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

class fragment_korean : Fragment(){
    private lateinit var  FKrecyclerview : RecyclerView
    private var mpadapter1: rv_Adapter = rv_Adapter(R.layout.comment_item)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var korean_listview = inflater.inflate(R.layout.korean_list, container, false)
        var thiscontext = container!!.getContext()
        FKrecyclerview = korean_listview.findViewById(R.id.korean_recyclerview)


        var commentList = arrayListOf<CommentItem>(
            CommentItem("kim","2020.01.01","wowww~",1),
            CommentItem("yeo","2020.01.02","wowww~",37),
            CommentItem("kang","2020.03.01","wowww~",10000),
            CommentItem("na","2020.12.22","wowww~",777),
            CommentItem("oh","2020.03.11","wowww~",3),
            CommentItem("dong","2020.07.23","wowww~",43),
            CommentItem("hu","2020.09.31","wowww~",55)
        )

        var mp_datalist = ArrayList<ArrayList<CommentItem>>()

        //리사이클러뷰의 어댑터 세팅
        FKrecyclerview.adapter=mpadapter1

        //리사이클러뷰 배치
        val lm = LinearLayoutManager(thiscontext)
        FKrecyclerview.layoutManager=lm

        mpadapter1.data=commentList
        mp_datalist.add(mpadapter1.data)

        mpadapter1.notifyDataSetChanged()


        return korean_listview
    }

}