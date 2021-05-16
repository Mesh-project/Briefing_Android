package com.example.briefing_android.summary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R
import com.example.briefing_android.api.CommentURLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.summary.recyclerview_comment.CommentItem
import com.example.briefing_android.summary.recyclerview_comment.rv_Adapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class fragment_korean(url:String) : Fragment(){
    private lateinit var  FKrecyclerview : RecyclerView
    private var mpadapter1: rv_Adapter = rv_Adapter(R.layout.comment_item)
    var mp_datalist = ArrayList<ArrayList<CommentItem>>()
    var commentList = arrayListOf<CommentItem>()
    var positive_commentList = arrayListOf<CommentItem>()
    var videourl=url

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var korean_listview = inflater.inflate(R.layout.korean_list, container, false)
        var thiscontext = container!!.getContext()
        FKrecyclerview = korean_listview.findViewById(R.id.korean_recyclerview)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .build()

//        val retrofit = Retrofit.Builder()
//            .baseUrl("")
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

        server(thiscontext)

        var flag=0
        var btn_filter : Button = korean_listview.findViewById(R.id.btn_filter)
        btn_filter.setOnClickListener(View.OnClickListener {
            if(btn_filter.text.toString().equals("ON")){
                if(flag==0){
                    positive_server(thiscontext)
                    flag=1
                }
                else{
                    mpadapter1.data=positive_commentList
                    mpadapter1.notifyDataSetChanged()
                }
            }
            else{
                mpadapter1.data=commentList
                mpadapter1.notifyDataSetChanged()
            }

        })


        return korean_listview
    }

    private fun server(thiscontext: Context){
        mpadapter1.notifyDataSetChanged()

        val callcommentpost = UserServiceImpl.CommentService.requestURL(CommentURLRequest = CommentURLRequest(videourl))
        callcommentpost.safeEnqueue {
            if(it.isSuccessful){

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

                //리사이클러뷰의 어댑터 세팅
                FKrecyclerview.adapter=mpadapter1
                //리사이클러뷰 배치
                val lm = LinearLayoutManager(thiscontext)
                FKrecyclerview.layoutManager=lm
                mpadapter1.data=commentList
                mpadapter1.notifyDataSetChanged()
                mp_datalist.add(mpadapter1.data)

            }
        }
        mpadapter1.notifyDataSetChanged()
    }

    private fun positive_server(thiscontext: Context){
        mpadapter1.notifyDataSetChanged()

        val callcommentpost = UserServiceImpl.CommentService.requestURL(CommentURLRequest = CommentURLRequest(videourl))
        callcommentpost.safeEnqueue {
            if(it.isSuccessful) {
                val korean_positive_CommentList = it.body()!!.korean_data
                for (i in 0 until korean_positive_CommentList.size) {
                    if (korean_positive_CommentList[i].predict.substring(11, 13).equals("긍정")) {
                        positive_commentList.add(
                            CommentItem(
                                it_username = korean_positive_CommentList[i].nickname,
                                it_date = korean_positive_CommentList[i].writetime.substring(0, 10),
                                it_comment = korean_positive_CommentList[i].comment,
                                it_likecount = korean_positive_CommentList[i].likecount
                            )
                        )
                    }
                }
                //리사이클러뷰의 어댑터 세팅
                FKrecyclerview.adapter = mpadapter1

                //리사이클러뷰 배치
                val lm = LinearLayoutManager(thiscontext)
                FKrecyclerview.layoutManager = lm
                mpadapter1.data = positive_commentList
                mpadapter1.notifyDataSetChanged()
                mp_datalist.add(mpadapter1.data)
            }
        }
        mpadapter1.notifyDataSetChanged()
    }
}