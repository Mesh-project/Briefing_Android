package com.example.briefing_android.summary.comment

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.briefing_android.R
import com.example.briefing_android.api.CommentURLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.sign.SignUpIdActivity
import com.example.briefing_android.summary.Comment_Viewpager_adapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.tabs.TabLayout

class CommentActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        var comment_viewPager = findViewById<ViewPager>(R.id.comment_viewPager)
        var comment_tablayout = findViewById<TabLayout>(R.id.comment_tablayout)

        var piechart : PieChart = findViewById(R.id.piechart)
        var piechart2 : PieChart = findViewById(R.id.piechart2)
        var url = intent.getStringExtra("url")




        //그래프 그리기
        //piechart1
        piechart.setUsePercentValues(true)
        piechart.getDescription().setEnabled(false)
        piechart.setExtraOffsets(5f, 10f, 5f, 5f)

        piechart.setDragDecelerationFrictionCoef(0.95f)

        piechart.setDrawHoleEnabled(false)
        piechart.setHoleColor(Color.WHITE)
        piechart.setTransparentCircleRadius(61f)

        val yValues = ArrayList<PieEntry>()
        val chart2_yValues = ArrayList<PieEntry>()

        //piechart2
        piechart2.setUsePercentValues(true)
        piechart2.getDescription().setEnabled(false)
        piechart2.setExtraOffsets(5f, 10f, 5f, 5f)

        piechart2.setDragDecelerationFrictionCoef(0.95f)

        piechart2.setDrawHoleEnabled(false)
        piechart2.setHoleColor(Color.WHITE)
        piechart2.setTransparentCircleRadius(61f)


        var name_list= listOf<String>("한국어","영어","그외","긍정","부정")

        val callcommentpost = UserServiceImpl.CommentService.requestURL(CommentURLRequest= CommentURLRequest(url))
        callcommentpost.safeEnqueue {
            if(it.isSuccessful){
                val chart_count = it.body()!!.count

                for(i in 0 until 3){
                    yValues.add(PieEntry(chart_count[i].toFloat(), name_list[i]))
                }
                for(i in 3 until chart_count.size){
                    chart2_yValues.add(PieEntry(chart_count[i].toFloat(), name_list[i]))
                }
            }



        val description = Description()
        description.setText("Language") //라벨
        description.setPosition(450f,40f)
        description.setTextSize(15f)
        piechart.setDescription(description)

        piechart.animateY(1000, Easing.EaseInOutCubic) //애니메이션


        val dataSet = PieDataSet(yValues, "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        val colors = ArrayList<Int>()
        val cor1 = ContextCompat.getColor(this, R.color.colorMain);
        val cor2 = ContextCompat.getColor(this, R.color.colorBase);
        val cor3 = ContextCompat.getColor(this, R.color.colorAccent);
        colors.add(cor1)
        colors.add(cor2)
        colors.add(cor3)
        dataSet.setColors(colors)

        val data = PieData(dataSet)
        data.setValueTextSize(13f)
        data.setValueTextColor(Color.BLACK)

        piechart.legend.isEnabled=false
        piechart.setData(data)




        val description2 = Description()
        description2.setText("Emotion") //라벨
        description2.setPosition(450f,50f)
        description2.setTextSize(15f)
        piechart2.setDescription(description2)

        piechart2.animateY(1000, Easing.EaseInOutCubic) //애니메이션


        val dataSet2 = PieDataSet(chart2_yValues, "")
        dataSet2.sliceSpace = 3f
        dataSet2.selectionShift = 5f
        val colors2 = ArrayList<Int>()
        val cor2_1 = ContextCompat.getColor(this, R.color.colorAccent);
        val cor2_2 = ContextCompat.getColor(this, R.color.colorBase);
        colors2.add(cor2_1)
        colors2.add(cor2_2)
        dataSet2.setColors(colors2)

        val data2 = PieData(dataSet2)
        data2.setValueTextSize(13f)
        data2.setValueTextColor(Color.BLACK)

        piechart2.legend.isEnabled=false
        piechart2.setData(data2)



        //댓글창 프래그먼트 뷰페이저
        val commnt_fragmentAdapter = Comment_Viewpager_adapter(supportFragmentManager,url)
        comment_viewPager.adapter = commnt_fragmentAdapter
        comment_tablayout.setupWithViewPager(comment_viewPager)

    }
}
}