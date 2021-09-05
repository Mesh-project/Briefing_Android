package com.example.briefing_android.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface analysisInter{
    @POST("/api/analysis/")
    fun requestURL(
        @Body urlRequest: URLRequest
    ): Call<URLResponse>
}

data class URLRequest(
   @SerializedName("user_idx")
   val user_idx : Int,
   @SerializedName("url")
   val url : String

)

data class URLResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data : analysis_info
)

data class analysis_info(
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
    val topic : String,
    @SerializedName("script")
    val script : String,
    @SerializedName("wordcloud")
    val wordcloud : String,
    @SerializedName("topword")
    val topword : List<String>
)