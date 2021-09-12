package com.example.briefing_android.summary.recyclerview_comment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.briefing_android.R

class rv_ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val username : TextView = view.findViewById(R.id.user_name)
    val date: TextView = view.findViewById(R.id.date)
    val comment : TextView = view.findViewById(R.id.user_comment)
    val likecount : TextView = view.findViewById(R.id.like_count)
    val icon : ImageView = view.findViewById(R.id.em_icon)
    val percent : TextView = view.findViewById(R.id.emotion_p)
    val profile : TextView = view.findViewById(R.id.profile)


    fun bind(data:CommentItem){
        username.text=data.it_username
        comment.text=data.it_comment
        likecount.text=data.it_likecount.toString()
        date.text=data.it_date
        emotionIcon(data.it_emotion) // 긍정부정 이모지
        percent.text=data.it_emotionp
        profile.setText(data.it_username.toString().substring(0,1))
    }

    fun emotionIcon(emotion:String){
        // 긍정부정 이모지
        if(emotion == "긍정"){
            Glide.with(itemView)
                    .load(R.drawable.happy)
                    .into(icon)
        }else if(emotion == "부정"){
            Glide.with(itemView)
                    .load(R.drawable.sad)
                    .into(icon)
        }
    }

}