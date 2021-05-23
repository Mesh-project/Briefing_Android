package com.example.briefing_android.main


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.briefing_android.R

class ht_ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    val histroy_thumnail: ImageView = view.findViewById(R.id.history_image)
    val history_title: TextView = view.findViewById(R.id.history_title)
    val history_date: TextView = view.findViewById(R.id.history_date)
    val history_channel: TextView = view.findViewById(R.id.history_channel)


    fun bind(data: ListItem){
        Glide.with(itemView)
                .load(data.ht_thumbnail)
                .into(histroy_thumnail)
        history_title.text = data.ht_title
        history_date.text = data.ht_analysis_date
        history_channel.text = data.ht_channel_name
    }
}