package com.example.briefing_android.summary

import android.content.Context
import android.os.Bundle
import android.util.Log
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

class fragment_korean : Fragment(){
    private lateinit var  FKrecyclerview : RecyclerView
    private var mpadapter1: rv_Adapter = rv_Adapter(R.layout.comment_item)
    var mp_datalist = ArrayList<ArrayList<CommentItem>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var korean_listview = inflater.inflate(R.layout.korean_list, container, false)
        var thiscontext = container!!.getContext()
        FKrecyclerview = korean_listview.findViewById(R.id.korean_recyclerview)


//        var commentList = arrayListOf<CommentItem>(
//            CommentItem("김","2020.01.01","멋지다~",1),
//            CommentItem("여","2020.01.02","슬프다~",37),
//            CommentItem("강","2020.03.01","나쁘다~",10000),
//            CommentItem("나","2020.12.22","이상하다~",777),
//            CommentItem("오","2020.03.11","좋다~",3),
//            CommentItem("동","2020.07.23","별로다~",43),
//            CommentItem("한","2020.09.31","멋지다~",55),
//            CommentItem("민","2020.01.01","멋지다~",1),
//            CommentItem("최","2020.01.02","멋지다~",37),
//            CommentItem("박","2020.03.01","멋지다~",10000),
//            CommentItem("이","2020.12.22","멋지다~",777),
//            CommentItem("윤","2020.03.11","좋다~",3),
//            CommentItem("민","2020.07.23","좋다~",43),
//            CommentItem("서","2020.09.31","좋다~",55)
//        )

//        var malicious_commentList = arrayListOf<CommentItem>(
//            CommentItem("김","2020.01.01","악플~",1),
//            CommentItem("여","2020.01.02","악플~",37),
//            CommentItem("강","2020.03.01","악플~",10000),
//            CommentItem("나","2020.12.22","악플~",777),
//            CommentItem("오","2020.03.11","악플~",3),
//            CommentItem("동","2020.07.23","악플~",43),
//            CommentItem("후","2020.09.31","악플~",55),
//        )



//        var btn_filter : Button  = korean_listview.findViewById(R.id.btn_filter)
//        btn_filter.setOnClickListener(View.OnClickListener {
//            if(btn_filter.text.toString().equals("ON")){
//                mpadapter1.data=malicious_commentList
//                mp_datalist.add(mpadapter1.data)
//                mpadapter1.notifyDataSetChanged()
//
//            }
//            else{
//                mpadapter1.data=commentList
//                mp_datalist.add(mpadapter1.data)
//                mpadapter1.notifyDataSetChanged()
//
//            }
//
//        })

        server(thiscontext)



        return korean_listview
    }

    private fun server(thiscontext: Context){
        mpadapter1.notifyDataSetChanged()

        val callcommentpost = UserServiceImpl.CommentService.requestURL(CommentURLRequest = CommentURLRequest("V1WHgI2xM2k"))
        callcommentpost.safeEnqueue {
            if(it.isSuccessful){
                var commentList = arrayListOf<CommentItem>()
                val korean_CommentList = it.body()!!.korean_data
                for(i in 0 until korean_CommentList.size){
                    commentList.add(
                        CommentItem(
                            it_username=korean_CommentList[i].nickname,
                            it_date=korean_CommentList[i].writetime.substring(0,10),
                            it_comment=korean_CommentList[i].comment,
                            it_likecount=korean_CommentList[i].likecount
                        )
                    )
                }
                Log.v("111","111")
                //리사이클러뷰의 어댑터 세팅
                FKrecyclerview.adapter=mpadapter1

                //리사이클러뷰 배치
                val lm = LinearLayoutManager(thiscontext)
                FKrecyclerview.layoutManager=lm
                Log.v("222","222")
                mpadapter1.data=commentList
                mpadapter1.notifyDataSetChanged()
                mp_datalist.add(mpadapter1.data)
                Log.v("333","333")
            }
        }
        mpadapter1.notifyDataSetChanged()
    }

}