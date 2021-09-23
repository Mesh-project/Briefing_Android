package com.example.briefing_android.summary.comment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.briefing_android.R
import com.example.briefing_android.api.CommentURLRequest
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class CommentGraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_graph)

        var piechart: PieChart = findViewById(R.id.piechart2)
        var piechart2: PieChart = findViewById(R.id.piechart)
        val yValues = ArrayList<PieEntry>()
        val chart2_yValues = ArrayList<PieEntry>()
        var name_list = listOf<String>("한국어", "영어", "그외", "긍정", "부정")
        var backbutton: ImageButton = findViewById(R.id.backbutton)
        backbutton.setOnClickListener {  // 뒤로가기 버튼
            finish()
        }

        // piechart1
        piechart.setUsePercentValues(true)
        piechart.getDescription().setEnabled(false)
        piechart.setExtraOffsets(5f, 10f, 5f, 5f)
        piechart.setDragDecelerationFrictionCoef(0.95f)
        piechart.setDrawHoleEnabled(false)
        piechart.setHoleColor(Color.WHITE)
        piechart.setTransparentCircleRadius(70f)

        // piechart2
        piechart2.setUsePercentValues(true)
        piechart2.getDescription().setEnabled(false)
        piechart2.setExtraOffsets(5f, 10f, 5f, 5f)
        piechart2.setDragDecelerationFrictionCoef(0.95f)
        piechart2.setDrawHoleEnabled(false)
        piechart2.setHoleColor(Color.WHITE)
        piechart2.setTransparentCircleRadius(70f)

        // server
        var url = intent.getStringExtra("url")
        val callcommentpost =
            UserServiceImpl.CommentService.requestURL(CommentURLRequest = CommentURLRequest(url))
        callcommentpost.safeEnqueue {
            if (it.isSuccessful) {
                Log.v("piechart", "서버 들어옴")
                val chart_count = it.body()!!.count

                for (i in 0 until 3) {
                    if (chart_count[i] != 0) {
                        yValues.add(PieEntry(chart_count[i].toFloat(), name_list[i]))
                        Log.v("piechart", chart_count[i].toString())
                    }
                }
                for (i in 3 until chart_count.size) {
                    if (chart_count[i] != 0) {
                        chart2_yValues.add(PieEntry(chart_count[i].toFloat(), name_list[i]))
                        Log.v("piechart", chart_count[i].toString())
                    }
                }
            }

            /*
            val description = Description()
            description.setText("Language") //라벨
            description.setPosition(600f, 100f)
            description.setTextSize(20f)
            piechart.setDescription(description)*/
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

            piechart.legend.isEnabled = false
            piechart.setData(data)


            /*
            val description2 = Description()
            description2.setText("Emotion") //라벨
            description2.setPosition(600f, 100f)
            description2.setTextSize(20f)
            piechart2.setDescription(description2)*/
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

            piechart2.legend.isEnabled = false

            piechart2.setData(data2)

        }
    }
}