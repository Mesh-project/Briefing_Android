package com.example.briefing_android.summary

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.briefing_android.R

class ViewPagerAdapter(private val context : Context) : PagerAdapter() {
    private var graphimg
            = arrayOf(R.drawable.ic_launcher_background,
                      R.drawable.ic_launcher_foreground) //그래프 이미지


    override fun getCount(): Int {
        return graphimg.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
       return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var imageView = ImageView(context)
        imageView.apply {
            scaleType=ImageView.ScaleType.CENTER_CROP
            setImageResource(graphimg[position])
        }
        container.addView(imageView,0)
        return imageView
//        container.addView(graphimg[position])
//        return graphimg[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }
}