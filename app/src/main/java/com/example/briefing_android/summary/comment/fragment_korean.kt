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
import android.widget.RadioGroup
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
    private lateinit var viewModel: SharedServerModel

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

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()) .get(SharedServerModel::class.java)

        Log.v("onCreateView", "호출")
        var korean_listview = inflater.inflate(R.layout.korean_list, container, false)
        //var thiscontext = container!!.getContext()
        var thiscontext = mContext!!
        FKrecyclerview = korean_listview.findViewById(R.id.korean_recyclerview)

        //progressON()
        //server(thiscontext)
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
        addcomentList()
        positive_commentList()


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

                val chart_count = it.body()!!.count
                for (i in 0 until chart_count.size) {
                    Log.v("chart_count[i]",  ": "+ chart_count[i])
                    when (i){
                        0->viewModel.koreansize.value = chart_count[0]
                        1->viewModel.englishsize.value = chart_count[1]
                        2->viewModel.etcsize.value = chart_count[2]
                        3->viewModel.positivesize.value = chart_count[3]
                        4->viewModel.negativesize.value = chart_count[4]
                    }

                }

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

    fun addcomentList(){
        progressON()
        Handler().postDelayed({
            commentList.add(
                CommentItem(
                    it_username = "조쿵쿵",
                    it_date = "2021.09.24",
                    it_comment = "저게 연봉보다 부럽다고",
                    it_likecount = 0,
                    it_emotion = "부정",
                    it_emotionp = "22.53%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "Enlios",
                    it_date = "2021.09.24",
                    it_comment = "사람은 의외로 사소한 거에 행복을 느끼고 거기서 의욕을 얻음 괜히 워라벨이나 직장문화로 회사를 선택하는 게 아님",
                    it_likecount = 0,
                    it_emotion = "부정",
                    it_emotionp = "0.89%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "Enlios",
                    it_date = "2021.09.24",
                    it_comment = "사람은 의외로 사소한 거에 행복을 느끼고 거기서 의욕을 얻음 괜히 워라벨이나 직장문화로 회사를 선택하는 게 아님",
                    it_likecount = 0,
                    it_emotion = "부정",
                    it_emotionp = "0.89%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "띠로리",
                    it_date = "2021.09.24",
                    it_comment = "식당은 안되고 구내식당은 씨제이가 다하고",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "88.97%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "Hello Hello",
                    it_date = "2021.09.24",
                    it_comment = "우리 군인 장병들은",
                    it_likecount = 0,
                    it_emotion = "부정",
                    it_emotionp = "96.34%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "팩트사나이",
                    it_date = "2021.09.24",
                    it_comment = "대기업이 아닌 난 라면에 김밥 먹는다",
                    it_likecount = 0,
                    it_emotion = "부정",
                    it_emotionp = "46.02%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "황성",
                    it_date = "2021.09.24",
                    it_comment = "국군장병에게도 좀",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "4.07%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "EJ",
                    it_date = "2021.09.24",
                    it_comment = "반찬이 많으면 버리는 음식도 많다는 사실 건강에도 좋지 않음",
                    it_likecount = 0,
                    it_emotion = "부정",
                    it_emotionp = "46.66%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "누렁소",
                    it_date = "2021.09.24",
                    it_comment = "저정도는 줘야지 고정손님 거저가져가는데 식단이라도 업그레이드되야지",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "82.31%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "핑크별똥별",
                    it_date = "2021.09.24",
                    it_comment = "음식만 맛있어도 회사가고 싶은 마음이 좀 더 생기지 않을까",
                    it_likecount = 2,
                    it_emotion = "부정",
                    it_emotionp = "82.31%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "thesky E",
                    it_date = "2021.09.24",
                    it_comment = "혼자 다 먹을 수 있냐 돼지가 따로 없네 쓰레기는 넘치는구나",
                    it_likecount = 0,
                    it_emotion = "부정",
                    it_emotionp = "79.19%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "hey ddo",
                    it_date = "2021.09.24",
                    it_comment = "저 돈으로 월급올려줘요",
                    it_likecount = 0,
                    it_emotion = "부정",
                    it_emotionp = "10.89%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "빈첸시오",
                    it_date = "2021.09.24",
                    it_comment = "아무리 그래도 연봉이 부럽지 ㅋㅋㅋ",
                    it_likecount = 39,
                    it_emotion = "긍정",
                    it_emotionp = "64.12%"
                )
            )

            commentList.add(
                CommentItem(
                    it_username = "Mf D",
                    it_date = "2021.09.24",
                    it_comment = "연봉 적은 회사는 저런 곳 없는데",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "61.93%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "최게바라",
                    it_date = "2021.09.24",
                    it_comment = "이얘기나올줄 알았지 진실은 모르고 그렇게 생각하고 있는 일반 사람들 많죠 삼성전자 식당 에버푸드 웰스토리 이재용 에버랜드 자회사 이재용이 주인 수위계약으로 독점 뉴스에선 퍼센트 단가가 높게 책정됐다고 나오지만 그 이상 한마디로 오너일가의 지갑돈쭐",
                    it_likecount = 0,
                    it_emotion = "부정",
                    it_emotionp = "39.77%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "차차차",
                    it_date = "2021.09.24",
                    it_comment = "와  \uD83D\uDC4D   어디여  ᆢ\uD83D\uDCAF",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "87.64%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "DDL",
                    it_date = "2021.09.24",
                    it_comment = "연봉이 더 부럽지",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "37.87%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "STALEATE",
                    it_date = "2021.09.24",
                    it_comment = "일부회사=대기업",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "67.87%"
                )
            )

            commentList.add(
                CommentItem(
                    it_username = "천상천하 유아독존",
                    it_date = "2021.09.24",
                    it_comment = "강추합니다!!!!!",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "98.93%"
                )
            )
            commentList.add(
                CommentItem(
                    it_username = "시간의숲",
                    it_date = "2021.09.24",
                    it_comment = "부럽다",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "95.28%"
                )
            )
        },3000)


        Log.v("summary","로딩 중지")
        Handler().postDelayed({
            progressOFF()
            mpadapter1.notifyDataSetChanged()
        },4000)


    }
    fun positive_commentList(){

        Handler().postDelayed({
            positive_commentList.add(
                CommentItem(
                    it_username = "띠로리",
                    it_date = "2021.09.24",
                    it_comment = "식당은 안되고 구내식당은 씨제이가 다하고",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "88.97%"
                )
            )
            positive_commentList.add(
                CommentItem(
                    it_username = "황성",
                    it_date = "2021.09.24",
                    it_comment = "국군장병에게도 좀",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "4.07%"
                )
            )
            positive_commentList.add(
                CommentItem(
                    it_username = "빈첸시오",
                    it_date = "2021.09.24",
                    it_comment = "아무리 그래도 연봉이 부럽지 ㅋㅋㅋ",
                    it_likecount = 39,
                    it_emotion = "긍정",
                    it_emotionp = "64.12%"
                )
            )
            positive_commentList.add(
                CommentItem(
                    it_username = "누렁소",
                    it_date = "2021.09.24",
                    it_comment = "저정도는 줘야지 고정손님 거저가져가는데 식단이라도 업그레이드되야지",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "82.31%"
                )
            )
            positive_commentList.add(
                CommentItem(
                    it_username = "Mf D",
                    it_date = "2021.09.24",
                    it_comment = "연봉 적은 회사는 저런 곳 없는데",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "61.93%"
                )
            )

            positive_commentList.add(
                CommentItem(
                    it_username = "천상천하 유아독존",
                    it_date = "2021.09.24",
                    it_comment = "강추합니다!!!!!",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "98.93%"
                )
            )

            positive_commentList.add(
                CommentItem(
                    it_username = "시간의숲",
                    it_date = "2021.09.24",
                    it_comment = "부럽다",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "95.28%"
                )
            )

            positive_commentList.add(
                CommentItem(
                    it_username = "차차차",
                    it_date = "2021.09.24",
                    it_comment = "와  \uD83D\uDC4D   어디여  ᆢ\uD83D\uDCAF",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "87.64%"
                )
            )

            positive_commentList.add(
                CommentItem(
                    it_username = "DDL",
                    it_date = "2021.09.24",
                    it_comment = "연봉이 더 부럽지",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "37.87%"
                )
            )

            positive_commentList.add(
                CommentItem(
                    it_username = "STALEATE",
                    it_date = "2021.09.24",
                    it_comment = "일부회사=대기업",
                    it_likecount = 0,
                    it_emotion = "긍정",
                    it_emotionp = "67.87%"
                )
            )

            mpadapter1.notifyDataSetChanged()
        },1000)
    }



}