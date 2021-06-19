package com.example.briefing_android.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CommentInter{
    @POST("/api/comment/")
    fun requestURL(
        @Body CommentURLRequest: CommentURLRequest
    ): Call<CommentURLResponse>
}

data class CommentURLRequest(
    @SerializedName("url")
    val url : String
)

data class CommentURLResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("count")
    val count : List<Int>,
    @SerializedName("korean_data")
    val korean_data : List<korean_data>,
    @SerializedName("etc_data")
    val etc_data : List<etc_data>,
)

data class korean_data(
    @SerializedName("0")
    val comment: String,
    @SerializedName("1")
    val nickname: String,
    @SerializedName("2")
    val writetime: String,
    @SerializedName("3")
    val likecount: Int,
    @SerializedName("sort")
    val sort: String,
    @SerializedName("predict")
    val predict: String,
)

data class etc_data(
    @SerializedName("0")
    val comment: String,
    @SerializedName("1")
    val nickname: String,
    @SerializedName("2")
    val writetime: String,
    @SerializedName("3")
    val likecount: Int,
    @SerializedName("sort")
    val sort: String
)