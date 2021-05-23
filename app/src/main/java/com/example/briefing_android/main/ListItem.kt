package com.example.briefing_android.main

data class ListItem (
    val ht_id: Int, //아이디
    val ht_url: String, //url
    val ht_thumbnail: String, //썸네일
    val ht_title : String,//제목
    val ht_analysis_date : String, //분석한날짜
    val ht_channel_name : String //채널명
    )