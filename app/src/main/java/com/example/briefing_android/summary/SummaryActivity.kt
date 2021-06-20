package com.example.briefing_android.summary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.briefing_android.R
import com.example.briefing_android.api.URLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.main.MainActivity
import com.example.briefing_android.sign.SignUpIdActivity
import com.example.briefing_android.summary.comment.CommentActivity
import com.example.briefing_android.summary.recyclerview_comment.Graph_Viewpager_adapter
import com.google.android.material.tabs.TabLayout
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SummaryActivity : AppCompatActivity() {
    private lateinit var btn_back: ImageButton
    var url: String =""
    private lateinit var channelname :TextView
    private lateinit var thumbnail :ImageButton
    private lateinit var video_time :TextView
    private lateinit var topic :TextView
    private lateinit var title :TextView
    var user_idx: Int =1
    var anal_str : String=""
    var analysis_idx:Int= 0 //히스토리에서 받아오는 id
    private lateinit var progressDialog: AppCompatDialog
    private lateinit var btn_comment : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        if (intent.getStringExtra("url")!=null){
            url = intent.getStringExtra("url")
        }
        anal_str = "https://www.youtube.com/watch?v=" + url
        Log.d("1.url value= ",url)
        Log.d("2.url value= ",anal_str)

        user_idx = MySharedPreferences.getUserIdx(this).toInt()
        Log.v("summaryactivity 확인","user_idx"+user_idx)

        analysis_idx = intent.getIntExtra("analysis_idx",0)
        Log.v("summaryactivity 확인","analysis+idx"+user_idx.toString())


        btn_comment = findViewById(R.id.btn_comment)
//        btn_comment.setOnClickListener(
//                View.OnClickListener {
//                    val intent = Intent(this, CommentActivity::class.java)
//                    intent.putExtra("url", url)
//                    startActivity(intent)
//                }
//        )

//        var indicator1 : ImageView = findViewById(R.id.indicator1)
//        var indicator2 : ImageView = findViewById(R.id.indicator2)



        channelname = findViewById(R.id.channelname)
        thumbnail= findViewById(R.id.btn_thumbnail)
        video_time  = findViewById(R.id.time)
        topic = findViewById(R.id.summary)
        title = findViewById(R.id.title)


        progressON()

        if(analysis_idx!=0){
            history_server(context=this)

        }
        else{
            server(context = this)
        }

        //그래프 뷰페이저
//        var viewpager = findViewById<ViewPager>(R.id.viewpager)
//        var viewpagerAdapter = ViewPagerAdapter(this)
//        viewpager.adapter=viewpagerAdapter
//
//        val graph_viewPager = findViewById<ViewPager>(R.id.viewpager)
//        val graph_fragmentAdapter = Graph_Viewpager_adapter(supportFragmentManager)
//        graph_viewPager.adapter = graph_fragmentAdapter
//
//        graph_viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
//            override fun onPageScrollStateChanged(state: Int) {}
//
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
//
//            override fun onPageSelected(position: Int) {
//                indicator1.setImageDrawable(getDrawable(R.drawable.shape_circle1))
//                indicator2.setImageDrawable(getDrawable(R.drawable.shape_circle1))
//
//                when(position){
//                    0-> indicator1.setImageDrawable(getDrawable(R.drawable.shape_circle2))
//                    1-> indicator2.setImageDrawable(getDrawable(R.drawable.shape_circle2))
//                }
//            }
//        })





        btn_back = findViewById(R.id.btn_back)
        // 1. 뒤로가기 버튼 이벤트
        btn_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        // 2. 유투브 영상으로 넘어가기
        thumbnail.setOnClickListener(View.OnClickListener {
            var Videourl = "https://www.youtube.com/watch?v=" + url
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(Videourl))
            startActivity(intent)
        })
    }

    fun server(context: Context){
    //-------------server-----------------
        print("함수호출")
        Log.v("anal_str value= ",anal_str)
        val analysispost = UserServiceImpl.AnalysisService.requestURL(urlRequest = URLRequest(anal_str,user_idx))

        analysispost.safeEnqueue {
            Log.v("분석 서버","들어옴")
            if(it.isSuccessful){
                progressOFF()
                Log.v("분석 서버","성공")
                val longtilte : String
                val VideoInfo = it.body()!!.data
                channelname.setText(VideoInfo.channel_name)
                if(VideoInfo.title.length>45){
                    longtilte = VideoInfo.title.substring(0,45) + "..."
                    title.setText(longtilte)
                }else{
                    title.setText(VideoInfo.title)
                }
                Glide.with(this)
                    .load(VideoInfo.thumbnail)
                    .override(1000,450)
                    .into(thumbnail)
                video_time.setText(VideoInfo.video_time)
                topic.setText(VideoInfo.topic)
            }
            btn_comment.setOnClickListener(
                    View.OnClickListener {
                        val intent = Intent(this, CommentActivity::class.java)
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
                progressOFF()
                Log.v("history detail 서버","성공")
                val longtilte : String
                val VideoInfo = it.body()!!.data
                channelname.setText(VideoInfo.channel_name)
                if(VideoInfo.title.length>45){
                    longtilte = VideoInfo.title.substring(0,45) + "..."
                    title.setText(longtilte)
                }else{
                    title.setText(VideoInfo.title)
                }
                Glide.with(this)
                        .load(VideoInfo.thumbnail)
                        .override(1000,450)
                        .into(thumbnail)
                video_time.setText(VideoInfo.video_time)
                topic.setText(VideoInfo.topic)

                url=VideoInfo.url
                Log.v("history_Detail 확인",VideoInfo.url)
                btn_comment.setOnClickListener(
                        View.OnClickListener {
                            val intent = Intent(this, CommentActivity::class.java)
                            Log.v("history_Detail 확인2",VideoInfo.url.substring(32,VideoInfo.url.length))
                            intent.putExtra("url", VideoInfo.url.substring(32,VideoInfo.url.length))
                            startActivity(intent)
                        }
                )
            }
        }
        //---------------------------------------
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
}