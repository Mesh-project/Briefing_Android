package com.example.briefing_android.summary

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import androidx.core.view.postDelayed
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
    private lateinit var progressDialog: AppCompatDialog
    var mp_datalist = ArrayList<ArrayList<CommentItem>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var english_listview = inflater.inflate(R.layout.english_list, container, false)
        var thiscontext = container!!.getContext()
        FENrecyclerview = english_listview.findViewById(R.id.english_recyclerview)


        progressON()
        server(thiscontext)


        return english_listview

  }
    private fun server(thiscontext: Context){
        mpadapter2.notifyDataSetChanged()

        val english_comment_List= UserServiceImpl.CommentService.requestURL(CommentURLRequest(videourl))
        english_comment_List.safeEnqueue {
            if(it.isSuccessful){
                progressOFF()
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
            else{
                progressOFF()
            }
        }
        mpadapter2.notifyDataSetChanged()
    }

    fun progressON(){
        progressDialog = AppCompatDialog(this.context)
        progressDialog.setCancelable(false)
        progressDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.dialog_layout)
        progressDialog.show()
        var img_loading_framge = progressDialog.findViewById<ImageView>(R.id.iv_frame_loading)
        var frameAnimation = img_loading_framge?.getBackground() as AnimationDrawable

        img_loading_framge?.post(object : Runnable{
            override fun run() {
                frameAnimation.start()
            }
        })
    }
    fun progressOFF(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss()
        }
    }
}