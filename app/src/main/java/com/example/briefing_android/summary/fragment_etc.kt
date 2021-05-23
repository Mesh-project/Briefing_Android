package com.example.briefing_android.summary

import android.app.LauncherActivity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R
import com.example.briefing_android.api.CommentURLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.summary.recyclerview_comment.CommentItem
import com.example.briefing_android.summary.recyclerview_comment.rv_Adapter

class fragment_etc(url:String) : Fragment(){
    private lateinit var  FErecyclerview : RecyclerView
    private var mpadapter3: rv_Adapter = rv_Adapter(R.layout.comment_item)
    private lateinit var progressDialog: AppCompatDialog
    private var mp_datalist = ArrayList<ArrayList<CommentItem>>()

    var url = url

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var etc_listview = inflater.inflate(R.layout.etc_list, container, false)
        var thiscontext = container!!.getContext()
        FErecyclerview = etc_listview.findViewById(R.id.etc_recyclerview)


        Log.v("url",url)
        progressON()
        server(thiscontext)


        return etc_listview
    }
    private fun server(thiscontext: Context){
        val callect_comment_List=UserServiceImpl.CommentService.requestURL(CommentURLRequest(url))
        callect_comment_List.safeEnqueue {
            if(it.isSuccessful){
                progressOFF()
                var ect_List = arrayListOf<CommentItem>()
                val ect_Comment = it.body()!!.etc_data
                for(i in 0 until ect_Comment.size){
                    if(ect_Comment[i].sort.equals("그외")){
                        ect_List.add(
                                CommentItem(
                                        it_username=ect_Comment[i].nickname,
                                        it_date=ect_Comment[i].writetime.substring(0,10),
                                        it_comment=ect_Comment[i].comment,
                                        it_likecount=ect_Comment[i].likecount
                                )
                        )
                    }
                }
                //리사이클러뷰의 어댑터 세팅
                FErecyclerview.adapter=mpadapter3

                //리사이클러뷰 배치
                val lm = LinearLayoutManager(thiscontext)
                FErecyclerview.layoutManager=lm

                mpadapter3.data = ect_List
                mpadapter3.notifyDataSetChanged()
                mp_datalist.add(mpadapter3.data)
            }
        }
        mpadapter3.notifyDataSetChanged()
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