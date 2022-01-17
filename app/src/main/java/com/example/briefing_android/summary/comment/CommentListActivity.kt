package com.example.briefing_android.summary.comment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.briefing_android.R
import com.example.briefing_android.api.CommentURLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.summary.recyclerview_comment.CommentItem
import com.example.briefing_android.summary.recyclerview_comment.rv_Adapter
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CommentListActivity : AppCompatActivity() {
    //var url = "sWuerIFLjjk"
    private lateinit var viewModel: SharedServerModel
    private var koreansize: Int = 0 // 한국어댓글수
    private var englishsize: Int = 0
    private var etcsize: Int = 0
    private var positivesize: Int = 0
    private var negativesize: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SharedServerModel::class.java)

        var tab_text : List<String> = listOf("한국어","외국어")

        //댓글창 프래그먼트 뷰페이저
        var url = intent.getStringExtra("url")

        //val comment_fragmentAdapter = Comment_Viewpager_adapter(supportFragmentManager, url)
        var comment_viewPager = findViewById<ViewPager2>(R.id.comment_viewPager)
        var comment_tablayout = findViewById<TabLayout>(R.id.comment_tablayout)
        val pagerAdapter = PagerFragmentStateAdapter(this, url)

        comment_viewPager.adapter = pagerAdapter
        // TabLayout attach
        TabLayoutMediator(comment_tablayout, comment_viewPager) { tab, position ->
            tab.text = tab_text[position]
        }.attach()

        var backbutton: ImageButton = findViewById(R.id.backbutton)
        backbutton.setOnClickListener { // 뒤로가기 버튼
            finish()
        }

        var btn_graph: Button = findViewById(R.id.btn_graph)
        btn_graph.setOnClickListener {
            // 파이차트 분석으로 넘어가기
            val intent = Intent(this, CommentGraphActivity::class.java)

            //더미
            koreansize=20
            englishsize=2
            etcsize=0
            positivesize=10
            negativesize=10


            intent.putExtra("koreansize", koreansize)
            intent.putExtra("englishsize", englishsize)
            intent.putExtra("etcsize", etcsize)
            intent.putExtra("positivesize", positivesize)
            intent.putExtra("negativesize", negativesize)

            Log.v("파이차트로 넘어가는 koreansize 확인", "222222//" + koreansize)
            Log.v("파이차트로 넘어가는 englishsize 확인", "222222//" + englishsize)
            Log.v("파이차트로 넘어가는 etcsize 확인", "222222//" + etcsize)
            Log.v("파이차트로 넘어가는 positivesize 확인", "222222//" + positivesize)
            startActivity(intent)
        }

        viewModel.koreansize.observe(this, Observer {
            koreansize = it
            Log.v("viewmodel koreansize 확인", "1111111//" + it) // 뷰모델에서 값 잘 받아왔는지 확인
        })
        viewModel.englishsize.observe(this, Observer {
            englishsize = it
            Log.v("viewmodel englishsize 확인", "1111111//" + it) // 뷰모델에서 값 잘 받아왔는지 확인
        })
        viewModel.etcsize.observe(this, Observer {
            etcsize = it
            Log.v("viewmodel etcsize 확인", "1111111//" + it) // 뷰모델에서 값 잘 받아왔는지 확인
        })
        viewModel.positivesize.observe(this, Observer {
            positivesize = it
            Log.v("viewmodel 긍정 확인", "1111111//" + it) // 뷰모델에서 값 잘 받아왔는지 확인
        })
        viewModel.negativesize.observe(this, Observer {
            negativesize = it
            Log.v("viewmodel 부정 확인", "1111111//" + it) // 뷰모델에서 값 잘 받아왔는지 확인
        })

    }

}