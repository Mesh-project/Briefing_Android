package com.example.briefing_android.summary

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.briefing_android.R
import com.example.briefing_android.api.URLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.main.MainActivity
import com.example.briefing_android.main.articleActivity
import com.example.briefing_android.sign.MySharedPreferences
import com.example.briefing_android.summary.comment.CommentActivity
import com.example.briefing_android.summary.comment.CommentListActivity

class SummaryActivity2: AppCompatActivity() {
    private lateinit var btn_back: ImageButton
    private lateinit var btn_content: Button
    private lateinit var btn_article: Button
    private lateinit var btn_play: ImageButton

    private lateinit var tv_title :TextView
    private lateinit var img_thumbnail : ImageView
    private lateinit var tv_channelname :TextView
    private lateinit var tv_time :TextView
    private lateinit var tv_summary :TextView
    private lateinit var btn_comment : Button

    var url: String =""

    var user_idx: Int =1
    var anal_str : String=""
    var analysis_idx:Int= 0 //히스토리에서 받아오는 id

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
        Log.v("summaryactivity 확인","user_idx"+user_idx)

        analysis_idx = intent.getIntExtra("analysis_idx",0)
        Log.v("summaryactivity 확인","analysis+idx"+user_idx.toString())



        if(analysis_idx!=0){
            history_server(context=this)

        }
        else{
            server(context = this)
        }

        tv_title = findViewById(R.id.tv_title)
        img_thumbnail= findViewById(R.id.img_thumbnail)
        tv_channelname  = findViewById(R.id.tv_channelname)
        tv_summary = findViewById(R.id.tv_summary)
        tv_time=findViewById(R.id.tv_time)

        btn_back = findViewById(R.id.btn_back)
        // 1. 뒤로가기 버튼 이벤트
        btn_back.setOnClickListener {
            finish()
        }

        btn_content=findViewById(R.id.btn_content)
        //2. 본문 보기 버튼
        btn_content.setOnClickListener {
            var intent = Intent(this, body_text::class.java)
            startActivity(intent)
        }

        btn_article=findViewById(R.id.btn_article)
        //3. 관련 기사 보기 버튼
        btn_article.setOnClickListener {
            var intent = Intent(this, articleActivity::class.java)
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
        print("함수호출")
        Log.v("anal_str value= ",anal_str)
        val analysispost = UserServiceImpl.AnalysisService.requestURL(urlRequest = URLRequest(anal_str,user_idx))

        analysispost.safeEnqueue {
            Log.v("분석 서버","들어옴")
            if(it.isSuccessful){
                //progressOFF()
                Log.v("분석 서버","성공")
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
                        .override(1000,450)
                        .into(img_thumbnail)
                tv_time.setText(VideoInfo.video_time)
                tv_summary.setText(VideoInfo.topic)
            }
            btn_comment.setOnClickListener(
                    View.OnClickListener {
                        val intent = Intent(this, CommentListActivity::class.java)
                        intent.putExtra("url", url)
                        startActivity(intent)
                    }
            )
        }
        //---------------------------------------
    }

    fun history_server(context: Context){
        //-------------server-----------------
        print("history detail 함수호출")
        val analysispost = UserServiceImpl.HistroyDetailService.responseHistory(analysis_idx)

        analysispost.safeEnqueue {
            Log.v("history detail 서버","들어옴")
            if(it.isSuccessful){
                //progressOFF()
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
                        .override(1000,450)
                        .into(img_thumbnail)
                tv_time.setText(VideoInfo.video_time)
                tv_summary.setText(VideoInfo.topic)

                url=VideoInfo.url
                Log.v("history_Detail 확인",VideoInfo.url)
                btn_comment.setOnClickListener(
                        View.OnClickListener {
                            val intent = Intent(this, CommentListActivity::class.java)
                            Log.v("history_Detail 확인2",VideoInfo.url.substring(32,VideoInfo.url.length))
                            intent.putExtra("url", VideoInfo.url.substring(32,VideoInfo.url.length))
                            startActivity(intent)
                        }
                )
            }
        }
        //---------------------------------------
    }
}