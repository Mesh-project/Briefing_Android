package com.example.briefing_android.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.briefing_android.R
import com.example.briefing_android.api.HistoryData
import com.example.briefing_android.api.SharedPreferenceController
import com.example.briefing_android.api.UserServiceImpl
import com.example.briefing_android.api.safeEnqueue
import com.example.briefing_android.sign.MySharedPreferences
import com.example.briefing_android.summary.SummaryActivity

class HistoryFragment : Fragment() {
    private var token: String = ""
    private lateinit var rv: RecyclerView
    private var rv_adapter: ht_Adapter = ht_Adapter(R.layout.item_history)
    var myhistorylist =ArrayList<ArrayList<ListItem>>()
    var myhistroylist_server = arrayListOf<HistoryData>()
    lateinit var mContext: Context
    private var user_idx: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_slide_page_history, container, false)

        //var thiscontext = container!!.getContext()
        rv = view.findViewById(R.id.history_recyclerView)

        //user_idx = MySharedPreferences.getUserIdx(mContext).toInt()
        Log.v("histroyfragment 확인","user_idx"+user_idx)

        server(mContext)

        return view
        //return inflater.inflate(R.layout.fragment_slide_page_main, container, false)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mContext = context
        }
    }

    // server
    fun server(thiscontext: Context) {
        token = SharedPreferenceController.getUserToken(thiscontext)
        val callHistory = UserServiceImpl.HistoryService.responseHistory(user_idx)
        callHistory.safeEnqueue {
            if (it.isSuccessful) {
                Log.v("히스토리 서버 연결", "성공")
                val myhistory = it.body()!!.data
                var hitoryList = arrayListOf<ListItem>()

                for (i in 0 until myhistory.size) {
                    hitoryList.add(
                            ListItem(
                                    ht_id = myhistory[i].analysis_idx,
                                    ht_url = myhistory[i].url,
                                    ht_thumbnail = myhistory[i].thumbnail,
                                    ht_title = myhistory[i].title,
                                    ht_analysis_date = myhistory[i].analysis_date,
                                    ht_channel_name = myhistory[i].channel_name
                            )
                    )
                    myhistroylist_server = myhistory as ArrayList<HistoryData>
                }
                // 리사이클러뷰 어댑터 세팅
                rv.adapter = rv_adapter
                // 리사이클러뷰 배치
                //rv.layoutManager = GridLayoutManager(context, 1)
                rv.layoutManager = LinearLayoutManager(context)
                //rv_adapter.data = repository.getRepoList() // 데이터줌
                rv_adapter.data = hitoryList
                rv_adapter.notifyDataSetChanged()
                myhistorylist.add(rv_adapter.data)

            }
        }
        rv_adapter.setItemClickListener(object : ht_Adapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                // summary액티비티로 이동
                Log.v("summary액티비티로 이동", "성공")
                val intent = Intent(getActivity(), SummaryActivity::class.java)
                startActivity(intent)
            }
        })

    }


}