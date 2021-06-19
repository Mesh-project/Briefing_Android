package com.example.briefing_android.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HistoryInter{
    @GET("/api/history/{user_idx}")
    fun responseHistory(
            //@Header("authorization") key : String // 토큰값
        @Path("user_idx") user_idx: Int
    ): Call<HistoryResponse>
}

data class HistoryResponse(
        @SerializedName("status")
        val status: Int,
        @SerializedName("data")
        val data: List<HistoryData>,
)

data class HistoryData(
        @SerializedName("analysis_idx")
        val analysis_idx: Int,
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