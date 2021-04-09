package com.example.briefing_android.main


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R

class ht_ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    val history_image: ImageView = view.findViewById(R.id.history_image)
    val history_title: TextView = view.findViewById(R.id.history_title)
    val history_date: TextView = view.findViewById(R.id.history_date)

    fun bind(data: HistoryItem){
        history_title.text = data.ht_title
        history_date.text = data.ht_date
    }
}