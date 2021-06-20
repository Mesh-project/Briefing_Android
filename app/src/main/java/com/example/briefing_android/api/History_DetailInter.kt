package com.example.briefing_android.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface History_DetailInter{
    @GET("/api/history_detail/{analysis_idx}")
    fun responseHistory(
            //@Header("authorization") key : String // 토큰값
            @Path("analysis_idx") analysis_idx: Int
    ): Call<HistoryDetailResponse>
}

data class HistoryDetailResponse(
        @SerializedName("status")
        val status: Int,
        @SerializedName("data")
        val data: HistoryDetailData,
)

data class HistoryDetailData(
        @SerializedName("user_idx")
        val user_idx: Int,
        @SerializedName("url")
        val url: String,
        @SerializedName("analysis_date")
        val analysis_date : String,
        @SerializedName("title")
        val title: String,
        @SerializedName("thumbnail")
        val thumbnail : String,
        @SerializedName("channel_name")
        val channel_name: String,
        @SerializedName("video_time")
        val video_time : String,
        @SerializedName("topic")
        val topic : String
)