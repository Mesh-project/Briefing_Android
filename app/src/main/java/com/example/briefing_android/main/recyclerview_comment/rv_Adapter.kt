package com.example.briefing_android.main.recyclerview_comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class rv_Adapter(rv_item: Int) :RecyclerView.Adapter<rv_ViewHolder>(){
    var data = arrayListOf<CommentItem>()
    val rv_item = rv_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rv_ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rv_item,parent,false)
        return rv_ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: rv_ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}