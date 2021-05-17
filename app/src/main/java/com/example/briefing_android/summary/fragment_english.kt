package com.example.briefing_android.summary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R
import com.example.briefing_android.api.CommentURLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.summary.recyclerview_comment.CommentItem
import com.example.briefing_android.summary.recyclerview_comment.rv_Adapter

class fragment_english(url:String) : Fragment(){
    private lateinit var  FENrecyclerview : RecyclerView
    private var mpadapter2: rv_Adapter = rv_Adapter(R.layout.comment_item)
    var videourl = url
    var commentList = arrayListOf<CommentItem>()
    var mp_datalist = ArrayList<ArrayList<CommentItem>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var english_listview = inflater.inflate(R.layout.english_list, container, false)
        var thiscontext = container!!.getContext()
        FENrecyclerview = english_listview.findViewById(R.id.english_recyclerview)

        server(thiscontext)

        return english_listview

  }
    private fun server(thiscontext: Context){
        mpadapter2.notifyDataSetChanged()

        val english_comment_List= UserServiceImpl.CommentService.requestURL(CommentURLRequest("V1WHgI2xM2k"))
        english_comment_List.safeEnqueue {
            if(it.isSuccessful){
                var english_List = arrayListOf<CommentItem>()
                val english__Comment = it.body()!!.etc_data
                for(i in 0 until english__Comment.size){
                    if(english__Comment[i].sort.equals("영어")){
                        english_List.add(
                                CommentItem(
                                        it_username=english__Comment[i].nickname,
                                        it_date=english__Comment[i].writetime.substring(0,10),
                                        it_comment=english__Comment[i].comment,
                                        it_likecount=english__Comment[i].likecount
                                )
                        )
                    }
                }
                //리사이클러뷰의 어댑터 세팅
                FENrecyclerview.adapter=mpadapter2

                //리사이클러뷰 배치
                val lm = LinearLayoutManager(thiscontext)
                FENrecyclerview.layoutManager=lm

                mpadapter2.data=english_List
                mpadapter2.notifyDataSetChanged()
                mp_datalist.add(mpadapter2.data)

            }
        }
        mpadapter2.notifyDataSetChanged()
    }
}