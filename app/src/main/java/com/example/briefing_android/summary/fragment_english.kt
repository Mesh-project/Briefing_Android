package com.example.briefing_android.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R
import com.example.briefing_android.summary.recyclerview_comment.CommentItem
import com.example.briefing_android.summary.recyclerview_comment.rv_Adapter

class fragment_english(url:String) : Fragment(){
    private lateinit var  FENrecyclerview : RecyclerView
    private var mpadapter2: rv_Adapter = rv_Adapter(R.layout.comment_item)
    var videourl = url

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var english_listview = inflater.inflate(R.layout.english_list, container, false)
        var thiscontext = container!!.getContext()
        FENrecyclerview = english_listview.findViewById(R.id.english_recyclerview)

        var commentList = arrayListOf<CommentItem>(
                CommentItem("kim","2020.01.01","wowww~",1),
                CommentItem("yeo","2020.01.02","Hi?",37),
                CommentItem("kang","2020.03.01","cool~",10000),
                CommentItem("na","2020.12.22","Good~!",777),
                CommentItem("oh","2020.03.11","Nice~",3),
                CommentItem("dong","2020.07.23","It's amazing!!!!",43),
                CommentItem("hu","2020.09.31","Really?",55),
                CommentItem("kim","2020.01.01","I like it",1),
                CommentItem("yeo","2020.01.02","wowww~",37),
                CommentItem("kang","2020.03.01","wowww~",10000))


        var mp_datalist = ArrayList<ArrayList<CommentItem>>()

        //리사이클러뷰의 어댑터 세팅
        FENrecyclerview.adapter=mpadapter2

        //리사이클러뷰 배치
        val lm = LinearLayoutManager(thiscontext)
        FENrecyclerview.layoutManager=lm

        mpadapter2.data=commentList
        mp_datalist.add(mpadapter2.data)

        mpadapter2.notifyDataSetChanged()


        return english_listview

  }

}