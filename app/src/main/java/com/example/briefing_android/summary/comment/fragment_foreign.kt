package com.example.briefing_android.summary.comment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
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

class fragment_foreign(url: String) : Fragment() {
    private lateinit var FENrecyclerview: RecyclerView
    private var mpadapter2: rv_Adapter = rv_Adapter(R.layout.comment_item2)
    var english_List = arrayListOf<CommentItem>()
    var ect_List = arrayListOf<CommentItem>()
    private lateinit var progressDialog: AppCompatDialog
    private var mp_datalist = ArrayList<ArrayList<CommentItem>>()
    var url = url
    lateinit var radioGroup : RadioGroup

    var activity: Activity? = null
    private var mContext: Context? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext=context
        if (context is Activity) activity = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var foreign_listview = inflater.inflate(R.layout.korean_list, container, false)
        var thiscontext = mContext!!
        FENrecyclerview = foreign_listview.findViewById(R.id.korean_recyclerview)

        var btn_english = foreign_listview.findViewById<RadioButton>(R.id.comment_all)
        var btn_etc = foreign_listview.findViewById<RadioButton>(R.id.comment_bad)
        btn_english.setText("영어")
        btn_etc.setText("그외")

        Log.v("프래그먼트(외국어)", "로딩시작")
        //progressON()
        //server(thiscontext)



        // 악플 필터링 라디오버튼 이벤트
        radioGroup = foreign_listview.findViewById(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            Log.v("댓글라디오 버튼", "라디오버튼 선택")
            if (checkedId == R.id.comment_all) { // 전체 댓글 보기
                mpadapter2.data = english_List
                mpadapter2.notifyDataSetChanged()
                Toast.makeText(thiscontext, "영어 댓글 보기", Toast.LENGTH_SHORT).show()
            }else if (checkedId == R.id.comment_bad) // 악플제거 댓글 보기
                mpadapter2.data = ect_List
            mpadapter2.notifyDataSetChanged()
            Toast.makeText(thiscontext, "그외 댓글만 보기", Toast.LENGTH_SHORT).show()
        }

        //더미
        addenglishList()



        //리사이클러뷰의 어댑터 세팅
        FENrecyclerview.adapter = mpadapter2
        //리사이클러뷰 배치
        val lm = LinearLayoutManager(context)
        FENrecyclerview.layoutManager = lm
        mpadapter2.data = english_List
        mp_datalist.add(mpadapter2.data)

        //progressOFF()

        return foreign_listview
    }

    private fun server(thiscontext: Context) {
        mpadapter2.notifyDataSetChanged()

        val foreign_comment_List = UserServiceImpl.CommentService.requestURL(CommentURLRequest(url))
        foreign_comment_List.safeEnqueue {
            if (it.isSuccessful) {
                progressOFF()
                Log.v("외국어 서버", "성공")
                val foreign_comment = it.body()!!.etc_data

                for (i in 0 until foreign_comment.size) {
                    if (foreign_comment[i].sort.equals("영어")) {
                        english_List.add(
                                CommentItem(
                                        it_username = foreign_comment[i].nickname,
                                        it_date = foreign_comment[i].writetime.substring(0, 10),
                                        it_comment = foreign_comment[i].comment,
                                        it_likecount = foreign_comment[i].likecount,
                                        it_emotion = "",
                                        it_emotionp = ""
                                )
                        )
                    }
                    else if(foreign_comment[i].sort.equals("그외")){
                        ect_List.add(
                                CommentItem(
                                        it_username=foreign_comment[i].nickname,
                                        it_date=foreign_comment[i].writetime.substring(0,10),
                                        it_comment=foreign_comment[i].comment,
                                        it_likecount=foreign_comment[i].likecount,
                                        it_emotion = "",
                                        it_emotionp = ""
                                )
                        )
                    }
                }

                //리사이클러뷰의 어댑터 세팅
                FENrecyclerview.adapter = mpadapter2
                //리사이클러뷰 배치
                val lm = LinearLayoutManager(thiscontext)
                FENrecyclerview.layoutManager = lm

                mpadapter2.data = english_List
                mpadapter2.notifyDataSetChanged()
                mp_datalist.add(mpadapter2.data)

            } else {
                progressOFF()
            }
        }
        mpadapter2.notifyDataSetChanged()
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
                frameAnimation.start()
            }

        })
    }

    fun progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss()
        }

    }
    fun addenglishList(){

        progressON()
        
        Handler().postDelayed({
            english_List.add(
                CommentItem(
                    it_username = "Tuwiti karera",
                    it_date = "2021-09-24",
                    it_comment = "AISURU.TOKYO/KARARE 18 years and over Youtube: This is fineSomeone: Says 'heck' Yourube: Be gone#",
                    it_likecount = 0,
                    it_emotion = "",
                    it_emotionp = ""
                )
            )
            english_List.add(
                CommentItem(
                    it_username = "Tuwiti karera",
                    it_date = "2021-09-24",
                    it_comment = "AISURU.TOKYO/KARARE 18 years and over Youtube: This is fineSomeone: Says 'heck' Yourube: Be gone#",
                    it_likecount = 0,
                    it_emotion = "",
                    it_emotionp = ""
                )
            )
            english_List.add(
                CommentItem(
                    it_username = "Tuwiti karera",
                    it_date = "2021-09-24",
                    it_comment = "AISURU.TOKYO/KARARE 18 years and over Youtube: This is fineSomeone: Says 'heck' Yourube: Be gone#",
                    it_likecount = 0,
                    it_emotion = "",
                    it_emotionp = ""
                )
            )

        },1000)

        Handler().postDelayed({
            progressOFF()
            mpadapter2.notifyDataSetChanged()
        },2000)
    }
    fun addectList(){

    }
}