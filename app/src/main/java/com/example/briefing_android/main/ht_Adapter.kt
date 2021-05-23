package com.example.briefing_android.main


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R

class ht_Adapter(rv_item:Int) : RecyclerView.Adapter<ht_ViewHolder>(){

    //데이터 저장할 아이템리스트
    //var data= listOf<HistoryItem>()
    var data= arrayListOf<ListItem>()
    val rv_item = rv_item

    //해당 아이템을 뷰홀더로 만드는 법
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ht_ViewHolder {
        //리스트 아이템 인플레이터
        val view = LayoutInflater.from(parent.context).inflate(rv_item,parent,false)
        return ht_ViewHolder(view)
    }

    //아이템 전체 갯수 파악
    override fun getItemCount(): Int {
        return data.size
        Log.v("리스트 아이템 전체 갯수: ", data.size.toString())
    }

    //바인드뷰홀더 생성
    override fun onBindViewHolder(holder: ht_ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener{
            itemClickListner.onClick(it,position)
        }
    }

    // 클릭 이벤트
    interface ItemClickListener{
        fun onClick(view: View, position: Int)
    }
    //를릭 리스너
    private lateinit var itemClickListner: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}