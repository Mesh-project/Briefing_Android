package com.example.briefing_android.summary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.bumptech.glide.Glide
import com.example.briefing_android.R
import com.example.briefing_android.api.URLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.main.articleActivity
import com.example.briefing_android.summary.comment.CommentListActivity

class SummaryActivity2: AppCompatActivity() {
    private lateinit var btn_back: ImageButton
    private lateinit var btn_content: Button
    private lateinit var btn_article: Button
    private lateinit var btn_play: ImageButton

    private lateinit var tv_title :TextView
    private lateinit var img_thumbnail : ImageView
    private lateinit var img_wordcloud : ImageView

    private lateinit var tv_channelname :TextView
    private lateinit var tv_time :TextView

    private lateinit var tv_summary :TextView
    private lateinit var btn_comment : Button

    private lateinit var progressDialog: AppCompatDialog

    private lateinit var em_icon2 : ImageView
    private lateinit var emotion_p2 : TextView

    var url: String =""

    var user_idx: Int =1
    var anal_str : String=""
    var analysis_idx:Int= 0 //히스토리에서 받아오는 id

    var script : String=""
    var topkey = List<String>(5,{""})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary2)


        if (intent.getStringExtra("url")!=null){
            url = intent.getStringExtra("url")
        }

        anal_str = "https://www.youtube.com/watch?v=" + url
        Log.d("1.url value= ",url)
        Log.d("2.url value= ",anal_str)

        //user_idx = MySharedPreferences.getUserIdx(this).toInt()
        Log.v("summaryactivity 확인","user_idx="+user_idx)

        analysis_idx = intent.getIntExtra("analysis_idx",0)
        Log.v("summaryactivity 확인","analysis_idx="+analysis_idx.toString())


        tv_title = findViewById(R.id.tv_title)
        img_thumbnail= findViewById(R.id.img_thumbnail)
        tv_channelname  = findViewById(R.id.tv_channelname)
        tv_summary = findViewById(R.id.tv_summary)
        tv_time=findViewById(R.id.tv_time)
        img_wordcloud=findViewById(R.id.img_wordcloud)
        em_icon2=findViewById(R.id.em_icon2)
        emotion_p2=findViewById(R.id.emotion_p2)


        //6. 서버 불러오기
        Log.v("summary","로딩시작")
        //progressON()

        Log.v("summary","setData")


        if(analysis_idx==0){
            Log.v("분석 서버","호출")
            Log.v("useridx 확인",user_idx.toString())
            //server(context = this)
            Setdata()
        }else{
            Log.v("히스토리 서버","호출")
            //history_server(context=this)
            tv_title.setText("[재택플러스] '연봉보다 부럽다'?‥'구내식당'의 변신")
            tv_channelname.setText("MBCNEWS")
            img_thumbnail.setImageResource(R.drawable.thumbnail)
            img_wordcloud.setImageResource(R.drawable.wordcloud2)
            tv_time.setText("00:02:01")
            tv_summary.setText(
                "한발 더 나아가 회사 식단으로 살도 빼고 혈당도 관히해주는 헬스케어 서비스를 제공하는 것도 있다고 하는데 코로나 이후로 회식이다 단체 식사가 어려워지면서 구내식당을 이용하는 직장인들이 자연스럽게 늘고 있다보니 생긴 또 다른 현상 중 하나인 셈인데 올해 사조원대로 훌쩍 성장한 직장 등 단체 급식 업체 시장의 경쟁도 점점 치열해지고 있다고 합니다. 이른바 성지명단에 오르기 위한 조건과 기준도 나름 있다는데요."
            )
            em_icon2.setImageResource(R.drawable.happy)
            emotion_p2.setText("61.47%")

        }

        btn_back = findViewById(R.id.btn_back)
        // 1. 뒤로가기 버튼 이벤트
        btn_back.setOnClickListener {
            finish()
        }

        btn_content=findViewById(R.id.btn_content)
        //2. 본문 보기 버튼
        btn_content.setOnClickListener {

            Log.v("script값 확인",script)

            var intent = Intent(this, body_text::class.java)
            intent.putExtra("script", script)
            intent.putExtra("tv_title", tv_title.text.toString())
            intent.putExtra("tv_channelname", tv_channelname.text.toString())
            startActivity(intent)
        }

        btn_article=findViewById(R.id.btn_article)
        //3. 관련 기사 보기 버튼
        btn_article.setOnClickListener {
            var intent = Intent(this, articleActivity::class.java)
            Log.v("topkey 확인",topkey.get(0))
            /*
            intent.putExtra("top1", topkey.get(0))
            intent.putExtra("top2", topkey.get(1))
            intent.putExtra("top3", topkey.get(2))
            intent.putExtra("top4", topkey.get(3))
            intent.putExtra("top5", topkey.get(4))
            */

            intent.putExtra("top1", "구내식당")
            intent.putExtra("top2", "직장")
            intent.putExtra("top3", "직장인")
            intent.putExtra("top4", "명단")
            intent.putExtra("top5", "회사")
            startActivity(intent)
        }

        btn_play=findViewById(R.id.btn_play)
        // 4. 유투브 영상으로 넘어가기
        btn_play.setOnClickListener(View.OnClickListener {
            var Videourl = "https://www.youtube.com/watch?v=" + url
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(Videourl))
            startActivity(intent)
        })

        btn_comment = findViewById(R.id.btn_comment)
        // 5. 댓글 보기버튼 이벤트
        btn_comment.setOnClickListener(
            View.OnClickListener {
                val intent = Intent(this, CommentListActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            }
        )
    }

    fun server(context: Context){
        //-------------server-----------------
        Log.v("함수호출","분석")
        Log.v("anal_str url= ",anal_str)
        val analysispost = UserServiceImpl.AnalysisService.requestURL(urlRequest = URLRequest(user_idx,anal_str))
        Log.v("22222",anal_str)
        analysispost.safeEnqueue {
            Log.v("분석 서버","들어옴")
            if(it.isSuccessful){
                progressOFF()
                Log.v("분석 서버","성공")
                val longtilte : String
                val VideoInfo = it.body()!!.data
                tv_channelname.setText(VideoInfo.channel_name)
                if(VideoInfo.title.length>50){
                    longtilte = VideoInfo.title.substring(0,50) + "..."
                    tv_title.setText(longtilte)
                }else{
                    tv_title.setText(VideoInfo.title)
                }
                Glide.with(this)
                        .load(VideoInfo.thumbnail)
                        .centerCrop()
                    .into(img_thumbnail)

                Glide.with(this)
                        .load(VideoInfo.wordcloud)
                        .override(1200,800)
                       .into(img_wordcloud)
                tv_time.setText(VideoInfo.video_time)
                tv_summary.setText(VideoInfo.topic)
                script=VideoInfo.script
                topkey=VideoInfo.topword.split(" ")

                // 주제 긍정부정 받아오기 // '긍정 xx.xx%'
                emotionIcon(VideoInfo.script_predict.substring(0,2)) // 긍정부정 이모티콘 넣기
                emotion_p2.setText(VideoInfo.script_predict.substring(3, VideoInfo.script_predict.length)) // xx.xx%까지 잘라냄
            }
        }
    }

    fun history_server(context: Context){
        //-------------server-----------------
        Log.v("history detail","함수호출")
        val analysispost = UserServiceImpl.HistroyDetailService.responseHistory(analysis_idx)
        Log.v("history detail","22222222")
        analysispost.safeEnqueue {
            Log.v("history detail 서버","들어옴")
            if(it.isSuccessful){
                progressOFF()
                Log.v("history detail 서버","성공")
                val longtilte : String
                val VideoInfo = it.body()!!.data
                tv_channelname.setText(VideoInfo.channel_name)
                if(VideoInfo.title.length>45){
                    longtilte = VideoInfo.title.substring(0,45) + "..."
                    tv_title.setText(longtilte)
                }else{
                    tv_title.setText(VideoInfo.title)
                }
                Glide.with(this)
                        .load(VideoInfo.thumbnail)
                    .centerCrop()
                        .into(img_thumbnail)
                Glide.with(this)
                        .load(VideoInfo.wordcloud)
                        .override(1200,800)
                        .into(img_wordcloud)
                tv_time.setText(VideoInfo.video_time)
                tv_summary.setText(VideoInfo.topic)
                script=VideoInfo.script
                topkey=VideoInfo.topword.split(" ")

                // 주제 긍정부정 받아오기 // '긍정 xx.xx%'
                emotionIcon(VideoInfo.script_predict.substring(0,2)) // 긍정부정 이모티콘 넣기
                emotion_p2.setText(VideoInfo.script_predict.substring(3, VideoInfo.script_predict.length)) // xx.xx%까지 잘라냄


                url=VideoInfo.url
                Log.v("history_Detail 확인",VideoInfo.url)
                btn_comment.setOnClickListener(
                        View.OnClickListener {
                            val intent = Intent(this, CommentListActivity::class.java)
                            Log.v("history_Detail 댓글보기 버튼 url",VideoInfo.url.substring(32,VideoInfo.url.length))
                            intent.putExtra("url", VideoInfo.url.substring(32,VideoInfo.url.length))
                            startActivity(intent)
                        }
                )
            }
        }
    }

    fun progressON(){
        progressDialog = AppCompatDialog(this)
        progressDialog.setCancelable(true)
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
    fun emotionIcon(emotion:String) {
        if (emotion == "긍정") {
            Glide.with(this)
                .load(R.drawable.happy)
                .into(em_icon2)
        } else if (emotion == "부정") {
            Glide.with(this)
                .load(R.drawable.sad)
                .into(em_icon2)
        }
    }

    //더미데이터 넣기
    fun Setdata(){
        Log.v("setData","함수 들어옴")
        progressON()
        Handler().postDelayed({
            tv_title.setText("[재택플러스] '연봉보다 부럽다'?‥'구내식당'의 변신")
            tv_channelname.setText("MBCNEWS")
            img_thumbnail.setImageResource(R.drawable.thumbnail)
            img_wordcloud.setImageResource(R.drawable.wordcloud2)
            tv_time.setText("00:02:01")
            tv_summary.setText(
                "한발 더 나아가 회사 식단으로 살도 빼고 혈당도 관히해주는 헬스케어 서비스를 제공하는 것도 있다고 하는데 코로나 이후로 회식이다 단체 식사가 어려워지면서 구내식당을 이용하는 직장인들이 자연스럽게 늘고 있다보니 생긴 또 다른 현상 중 하나인 셈인데 올해 사조원대로 훌쩍 성장한 직장 등 단체 급식 업체 시장의 경쟁도 점점 치열해지고 있다고 합니다. 이른바 성지명단에 오르기 위한 조건과 기준도 나름 있다는데요."
            )
            em_icon2.setImageResource(R.drawable.happy)
            emotion_p2.setText("61.47%")
        },3000)


        Log.v("summary","로딩 중지")
        Handler().postDelayed({
            progressOFF()
        },4000)
    }

}