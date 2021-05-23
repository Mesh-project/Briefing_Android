package com.example.briefing_android.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface HistoryInter{
    @GET("/api/history/")
    fun responseHistory(
            //@Header("authorization") key : String // 토큰값
    ): Call<HistoryResponse>
}

data class HistoryResponse(
        @SerializedName("status")
        val status: Int,
        @SerializedName("data")
        val data: List<HistoryData>,
)

data class HistoryData(
        @SerializedName("id")
        val id: Int,
        @SerializedName("user_idx")
        val user_idx: Int,
        @SerializedName("url")
        val url: String,
        @SerializedName("title") // 제목
        val title: String,
        @SerializedName("thumbnail") // 썸네일
        val thumbnail: String,
        @SerializedName("analysis_date") // 분석한 날짜
        val analysis_date: String,
        @SerializedName("channel_name") // 채널명
        val channel_name: String,
)