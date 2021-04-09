package com.example.briefing_android.main


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R

class ht_Adapter(private val context: Context) : RecyclerView.Adapter<ht_ViewHolder>(){

    //데이터 저장할 아이템리스트
    var data= listOf<HistoryItem>()
   // val rv_item = rv_item

    //해당 아이템을 뷰홀더로 만드는 법
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ht_ViewHolder {
        //리스트 아이템 인플레이터
        val view = LayoutInflater.from(context).inflate(R.layout.item_history,parent,false)
        return ht_ViewHolder(view)
    }

    //아이템 전체 갯수 파악
    override fun getItemCount(): Int {
        return data.size
    }

    //바인드뷰홀더 생성
    override fun onBindViewHolder(holder: ht_ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}