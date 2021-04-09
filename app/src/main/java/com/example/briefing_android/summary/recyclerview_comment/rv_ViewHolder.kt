package com.example.briefing_android.summary.recyclerview_comment

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R

class rv_ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val username : TextView = view.findViewById(R.id.user_name)
    val comment : TextView = view.findViewById(R.id.user_comment)
    val likecount : TextView = view.findViewById(R.id.like_count)
    val date: TextView = view.findViewById(R.id.date)

    fun bind(data:CommentItem){
        username.text=data.it_username
        comment.text=data.it_comment
        likecount.text=data.it_likecount.toString()
        date.text=data.it_date
    }
}