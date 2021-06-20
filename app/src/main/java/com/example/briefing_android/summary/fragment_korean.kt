package com.example.briefing_android.summary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R
import com.example.briefing_android.api.CommentURLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.summary.comment.CommentActivity
import com.example.briefing_android.summary.recyclerview_comment.CommentItem
import com.example.briefing_android.summary.recyclerview_comment.rv_Adapter


class fragment_korean(url:String) : Fragment(){
    private lateinit var  FKrecyclerview : RecyclerView
    private var mpadapter1: rv_Adapter = rv_Adapter(R.layout.comment_item)
    var mp_datalist = ArrayList<ArrayList<CommentItem>>()
    var commentList = arrayListOf<CommentItem>()
    var positive_commentList = arrayListOf<CommentItem>()
    var ect_List = arrayListOf<CommentItem>()
    var english_List = arrayListOf<CommentItem>()
    private var url=url
    private lateinit var progressDialog: AppCompatDialog
    private lateinit var  iv_frame_loading : ImageView
    private lateinit var viewModel: SharedPiechartModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()) .get(SharedPiechartModel::class.java)


        var korean_listview = inflater.inflate(R.layout.korean_list, container, false)
        var thiscontext = container!!.getContext()
        FKrecyclerview = korean_listview.findViewById(R.id.korean_recyclerview)


        progressON()
        server(thiscontext)
        positive_server_count(thiscontext)

        var flag=0
        var btn_filter : Button = korean_listview.findViewById(R.id.btn_filter)
        btn_filter.setOnClickListener(View.OnClickListener {
            if(btn_filter.text.toString().equals("악플 ON")){
//                if(flag==0){
//                    progressON()
//                    positive_server(thiscontext)
//                    var t1 = Toast.makeText(thiscontext, "긍정 댓글만 보기", Toast.LENGTH_SHORT)
//                    t1.show()
//                    flag=1
//                }
//                else{
//                    mpadapter1.data=positive_commentList
//                    mpadapter1.notifyDataSetChanged()
//                    var t1 = Toast.makeText(thiscontext, "긍정 댓글만 보기", Toast.LENGTH_SHORT)
//                    t1.show()
//                }
                mpadapter1.data=positive_commentList
                mpadapter1.notifyDataSetChanged()
                var t1 = Toast.makeText(thiscontext, "긍정 댓글만 보기", Toast.LENGTH_SHORT)
                t1.show()
            }
            else{
                mpadapter1.data=commentList
                mpadapter1.notifyDataSetChanged()
                var t1 = Toast.makeText(thiscontext, "전체 댓글 보기", Toast.LENGTH_SHORT)
                t1.show()
            }

        })

        return korean_listview
    }

    private fun server(thiscontext: Context){
        mpadapter1.notifyDataSetChanged()


        val callcommentpost = UserServiceImpl.CommentService.requestURL(CommentURLRequest=CommentURLRequest(url))
        Log.v("korean comment url1",url)

        callcommentpost.safeEnqueue {
            if(it.isSuccessful){
                Log.v("댓글 서버(한국어)","성공")
                progressOFF()
                val korean_CommentList = it.body()!!.korean_data

                Log.v("intent","크기 데이터 보냄")


                //viewModel.koreansize.value = korean_CommentList.size // viewmodel에 korean댓글 수 넣음.
                //Log.v("koreansize 서버"+korean_CommentList.size,"--------------")


                val english__Comment = it.body()!!.etc_data
                //한국어
                for(i in 0 until korean_CommentList.size){
                    commentList.add(
                        CommentItem(
                            it_username=korean_CommentList[i].nickname,
                            it_date=korean_CommentList[i].writetime.substring(0,10),
                            it_comment=korean_CommentList[i].comment,
                            it_likecount=korean_CommentList[i].likecount
                        )
                    )
                    if (korean_CommentList[i].predict.equals("긍정")) {
                        positive_commentList.add(
                                CommentItem(
                                        it_username = korean_CommentList[i].nickname,
                                        it_date = korean_CommentList[i].writetime.substring(0, 10),
                                        it_comment = korean_CommentList[i].comment,
                                        it_likecount = korean_CommentList[i].likecount
                                )
                        )
                    }
                }


//                //영어
//                for(i in 0 until english__Comment.size){
//                    if(english__Comment[i].sort.equals("영어")){
//                        english_List.add(
//                                CommentItem(
//                                        it_username=english__Comment[i].nickname,
//                                        it_date=english__Comment[i].writetime.substring(0,10),
//                                        it_comment=english__Comment[i].comment,
//                                        it_likecount=english__Comment[i].likecount
//                                )
//                        )
//                    }else{
//                        //그외
//                        ect_List.add(
//                                CommentItem(
//                                        it_username=english__Comment[i].nickname,
//                                        it_date=english__Comment[i].writetime.substring(0,10),
//                                        it_comment=english__Comment[i].comment,
//                                        it_likecount=english__Comment[i].likecount
//                                )
//                        )
//                    }
//                }

                //viewModel.englishlist.postValue(english_List)

                //viewModel.englishlist.value= english_List[1] // viewmodel에 korean댓글 수 넣음.
                //Log.v("koreansize 서버"+korean_CommentList.size,"--------------")

                //리사이클러뷰의 어댑터 세팅
                FKrecyclerview.adapter=mpadapter1
                //리사이클러뷰 배치
                val lm = LinearLayoutManager(thiscontext)
                FKrecyclerview.layoutManager=lm
                mpadapter1.data=commentList
                mp_datalist.add(mpadapter1.data)

            }
        }
        mpadapter1.notifyDataSetChanged()
    }

    private fun positive_server(thiscontext: Context){
        mpadapter1.notifyDataSetChanged()

        val callcommentpost = UserServiceImpl.CommentService.requestURL(CommentURLRequest = CommentURLRequest(url))
        callcommentpost.safeEnqueue {
            if(it.isSuccessful) {
                progressOFF()
                val korean_positive_CommentList = it.body()!!.korean_data
                //viewModel.positivesize.value = korean_positive_CommentList.size // viewmodel에 korean댓글 수 넣음.
                for (i in 0 until korean_positive_CommentList.size) {
                    if (korean_positive_CommentList[i].predict.equals("긍정")) {
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
            else{
                progressOFF()
            }
        }
        mpadapter1.notifyDataSetChanged()
    }

    private fun positive_server_count(thiscontext: Context){
        val callcommentpost = UserServiceImpl.CommentService.requestURL(CommentURLRequest = CommentURLRequest(url))
        callcommentpost.safeEnqueue {
            if(it.isSuccessful) {
                progressOFF()
                val korean_positive_CommentList = it.body()!!.korean_data
                //var p_count =0
                for (i in 0 until korean_positive_CommentList.size) {
                    if (korean_positive_CommentList[i].predict.equals("긍정")) {
                       //p_count++
                    }
                }
                //viewModel.positivesize.value = p_count // viewmodel에 긍정댓글 수 넣음.
            }

        }
    }


    fun progressON(){
        progressDialog = AppCompatDialog(this.context)
        progressDialog.setCancelable(true)
        progressDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.dialog_layout)
        progressDialog.show()
        var img_loading_framge = progressDialog.findViewById<ImageView>(R.id.iv_frame_loading)
        var frameAnimation = img_loading_framge?.getBackground() as AnimationDrawable
        img_loading_framge?.post(object : Runnable{
            override fun run() {
                frameAnimation.setEnterFadeDuration(2000)
                frameAnimation.setExitFadeDuration(3000)
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