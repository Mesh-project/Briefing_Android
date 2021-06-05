package com.example.briefing_android.summary

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.utils.Easing
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.briefing_android.R
import com.github.mikephil.charting.animation.Easing.EaseInOutCubic
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class fragment_piechart : Fragment(){
    private lateinit var piechart : PieChart
    private lateinit var piechart2 : PieChart


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var piechart_layout = inflater.inflate(R.layout.fragment_piechart, container, false)

        var thiscontext = container!!.getContext()
        piechart = piechart_layout.findViewById(R.id.piechart)
        piechart2 = piechart_layout.findViewById(R.id.piechart2)

        //------------------------
        if(arguments != null){
            val bundle_value = arguments?.getString("key")
            Log.v("파이차트에서 받은 key=",bundle_value)
        }else{
            Log.v("파이차트에서 받은 key=","null")
        }
        //------------------------

        //piechart1
        piechart.setUsePercentValues(true)
        piechart.getDescription().setEnabled(false)
        piechart.setExtraOffsets(5f, 10f, 5f, 5f)

        piechart.setDragDecelerationFrictionCoef(0.95f)

        piechart.setDrawHoleEnabled(false)
        piechart.setHoleColor(Color.WHITE)
        piechart.setTransparentCircleRadius(61f)

        val yValues = ArrayList<PieEntry>()

        yValues.add(PieEntry(15f, "한국어"))
        yValues.add(PieEntry(45f, "영어"))
        yValues.add(PieEntry(50f, "기타"))

        val description = Description()
        description.setText("Language") //라벨
        description.setPosition(450f,40f)
        description.setTextSize(15f)
        piechart.setDescription(description)

        piechart.animateY(1000, EaseInOutCubic) //애니메이션


        val dataSet = PieDataSet(yValues, "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        val colors = ArrayList<Int>()
        val cor1 = ContextCompat.getColor(thiscontext, R.color.colorMain);
        val cor2 = ContextCompat.getColor(thiscontext, R.color.colorBase);
        val cor3 = ContextCompat.getColor(thiscontext, R.color.colorAccent);
        colors.add(cor1)
        colors.add(cor2)
        colors.add(cor3)
        dataSet.setColors(colors)

        val data = PieData(dataSet)
        data.setValueTextSize(13f)
        data.setValueTextColor(Color.BLACK)

        piechart.legend.isEnabled=false
        piechart.setData(data)

        //piechart2
        piechart2.setUsePercentValues(true)
        piechart2.getDescription().setEnabled(false)
        piechart2.setExtraOffsets(5f, 10f, 5f, 5f)

        piechart2.setDragDecelerationFrictionCoef(0.95f)

        piechart2.setDrawHoleEnabled(false)
        piechart2.setHoleColor(Color.WHITE)
        piechart2.setTransparentCircleRadius(61f)

        val chart2_yValues = ArrayList<PieEntry>()

        chart2_yValues.add(PieEntry(45f, "긍정"))
        chart2_yValues.add(PieEntry(55f, "부정"))


        val description2 = Description()
        description2.setText("Emotion") //라벨
        description2.setPosition(450f,50f)
        description2.setTextSize(15f)
        piechart2.setDescription(description2)

        piechart2.animateY(1000, EaseInOutCubic) //애니메이션


        val dataSet2 = PieDataSet(chart2_yValues, "")
        dataSet2.sliceSpace = 3f
        dataSet2.selectionShift = 5f
        val colors2 = ArrayList<Int>()
        val cor2_1 = ContextCompat.getColor(thiscontext, R.color.colorAccent);
        val cor2_2 = ContextCompat.getColor(thiscontext, R.color.colorBase);
        colors2.add(cor2_1)
        colors2.add(cor2_2)
        dataSet2.setColors(colors2)

        val data2 = PieData(dataSet2)
        data2.setValueTextSize(13f)
        data2.setValueTextColor(Color.BLACK)

        piechart2.legend.isEnabled=false
        piechart2.setData(data2)


        return piechart_layout
    }
}