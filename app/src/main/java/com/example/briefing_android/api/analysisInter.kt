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
    @SerializedName("title")
    val title: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("published")
    val published : String,
    @SerializedName("thumnail")
    val thumnail: String,
    @SerializedName("time")
    val time : String
)