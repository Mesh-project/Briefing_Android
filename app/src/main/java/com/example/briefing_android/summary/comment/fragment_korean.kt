package com.example.briefing_android.summary.comment

import android.app.Activity
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
import android.widget.RadioGroup
import android.widget.Toast
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

class fragment_korean(url: String) : Fragment() {
    private lateinit var FKrecyclerview: RecyclerView
    private var mpadapter1: rv_Adapter = rv_Adapter(R.layout.comment_item2)
    var mp_datalist = ArrayList<ArrayList<CommentItem>>()
    var commentList = arrayListOf<CommentItem>()
    var positive_commentList = arrayListOf<CommentItem>()
    private var url = url
    private lateinit var progressDialog: AppCompatDialog
    lateinit var radioGroup : RadioGroup

    var activity: Activity? = null
    private var mContext: Context?=null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.v("onAttach", "호출")
        mContext=context
        if (context is Activity) {
            activity = context
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Log.v("onCreateView", "호출")
        var korean_listview = inflater.inflate(R.layout.korean_list, container, false)
        //var thiscontext = container!!.getContext()
        var thiscontext = mContext!!
        FKrecyclerview = korean_listview.findViewById(R.id.korean_recyclerview)

        progressON()
        server(thiscontext)
        Log.v("프래그먼트(한국어)", "서버끝")

        // 악플 필터링 라디오버튼 이벤트
        radioGroup = korean_listview.findViewById(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            Log.v("댓글라디오 버튼", "라디오버튼 선택")
            if (checkedId == R.id.comment_all) { // 전체 댓글 보기
                mpadapter1.data = commentList
                mpadapter1.notifyDataSetChanged()
                Toast.makeText(thiscontext, "전체 댓글 보기", Toast.LENGTH_SHORT).show()
            } else if (checkedId == R.id.comment_bad) // 악플제거 댓글 보기
                mpadapter1.data = positive_commentList
            mpadapter1.notifyDataSetChanged()
            Toast.makeText(thiscontext, "긍정 댓글만 보기", Toast.LENGTH_SHORT).show()
        }

        //더미
        commentList.add(
                CommentItem(
                        it_username = "어쩌구",
                        it_date = "2021.08.20",
                        it_comment = "댓글내용입니다아0.댓글내용입니다.댓글내용입니다.댓글내용입니다.댓글내용입니다.댓글내용입니다.댓글내용입니다.댓글내용입니다.댓글내용입니다.댓글내용입니다.",
                        it_likecount = 123,
                        it_emotion = "긍정",
                        it_emotionp = "12.34%"
                )
        )
        FKrecyclerview.adapter = mpadapter1
        //리사이클러뷰 배치
        val lm = LinearLayoutManager(thiscontext)
        FKrecyclerview.layoutManager = lm
        mpadapter1.data = commentList
        mp_datalist.add(mpadapter1.data)

        return korean_listview
    }

    private fun server(thiscontext: Context) {
        mpadapter1.notifyDataSetChanged()

        val callcommentpost =
                UserServiceImpl.CommentService.requestURL(CommentURLRequest = CommentURLRequest(url))
        Log.v("korean comment url1", url)

        callcommentpost.safeEnqueue {
            if (it.isSuccessful) {
                Log.v("댓글 서버(한국어)", "성공")
                progressOFF()
                val korean_CommentList = it.body()!!.korean_data
                Log.v("intent", "크기 데이터 보냄")

                for (i in 0 until korean_CommentList.size) {
                    var str = korean_CommentList[i].predict // 'xx.xx% 긍정' 식으로
                    var emotion = str.substring(str.length - 2, str.length) // 긍정부정만 잘라냄
                    var percent = str.substring(0, str.length - 3) // xx.xx%까지 잘라냄

                    commentList.add(
                            CommentItem(
                                    it_username = korean_CommentList[i].nickname,
                                    it_date = korean_CommentList[i].writetime.substring(0, 10),
                                    it_comment = korean_CommentList[i].comment,
                                    it_likecount = korean_CommentList[i].likecount,
                                    it_emotion = emotion,
                                    it_emotionp = percent
                            )
                    )
                    if (emotion.equals("긍정")) {
                        positive_commentList.add(
                                CommentItem(
                                        it_username = korean_CommentList[i].nickname,
                                        it_date = korean_CommentList[i].writetime.substring(0, 10),
                                        it_comment = korean_CommentList[i].comment,
                                        it_likecount = korean_CommentList[i].likecount,
                                        it_emotion = emotion,
                                        it_emotionp = percent
                                )
                        )
                    }
                }
                //리사이클러뷰의 어댑터 세팅
                FKrecyclerview.adapter = mpadapter1
                //리사이클러뷰 배치
                val lm = LinearLayoutManager(thiscontext)
                FKrecyclerview.layoutManager = lm
                mpadapter1.data = commentList
                mp_datalist.add(mpadapter1.data)


            }
        }
        mpadapter1.notifyDataSetChanged()

    }

    fun progressON() {
        progressDialog = AppCompatDialog(this.context)
        progressDialog.setCancelable(true)
        progressDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.dialog_layout)
        progressDialog.show()
        var img_loading_framge = progressDialog.findViewById<ImageView>(R.id.iv_frame_loading)
        var frameAnimation = img_loading_framge?.getBackground() as AnimationDrawable
        img_loading_framge?.post(object : Runnable {
            override fun run() {
                frameAnimation.setEnterFadeDuration(2000)
                frameAnimation.setExitFadeDuration(3000)
                frameAnimation.start()
            }

        })
    }

    fun progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss()
        }
    }

}