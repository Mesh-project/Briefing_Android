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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var viewModel: SharedPiechartModel
    private var koreansize: Int = 0 // 한국어댓글수
    private var englishsize: Int = 100
    private var etcsize: Int = 0
    private var positivesize: Int = 0
    private var negativesize: Int = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var piechart_layout = inflater.inflate(R.layout.fragment_piechart, container, false)
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(SharedPiechartModel::class.java)

        var thiscontext = container!!.getContext()
        piechart = piechart_layout.findViewById(R.id.piechart)
        piechart2 = piechart_layout.findViewById(R.id.piechart2)

        //piechart1
        piechart.setUsePercentValues(true)
        piechart.getDescription().setEnabled(false)
        piechart.setExtraOffsets(5f, 10f, 5f, 5f)

        piechart.setDragDecelerationFrictionCoef(0.95f)

        piechart.setDrawHoleEnabled(false)
        piechart.setHoleColor(Color.WHITE)
        piechart.setTransparentCircleRadius(61f)

        val yValues = ArrayList<PieEntry>()

        viewModel.koreansize.observe(this, Observer{
            koreansize = it
            yValues.add(PieEntry(koreansize.toFloat(), "한국어"))
            Log.v("piechart koreansize 확인","1111111//"+it) // 뷰모델에서 값 잘 받아왔는지 확인
        })
        Log.v("piechart koreansize 확인","22222//"+koreansize) // koreansize에 저장 되었는지 확인

        viewModel.englishsize.observe(this, Observer{
            englishsize = it
            yValues.add(PieEntry(englishsize.toFloat(), "영어"))
            Log.v("piechart englishsize 확인","1111111//"+it) // 뷰모델에서 값 잘 받아왔는지 확인
        })
        Log.v("piechart englishsize 확인","22222//"+englishsize) // koreansize에 저장 되었는지 확인

        viewModel.etcsize.observe(this, Observer{
            etcsize = it
            yValues.add(PieEntry(etcsize.toFloat(), "기타"))
            Log.v("piechart etcsize 확인","1111111//"+it) // 뷰모델에서 값 잘 받아왔는지 확인
        })
        Log.v("piechart etcsize 확인","22222//"+englishsize) // koreansize에 저장 되었는지 확인


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