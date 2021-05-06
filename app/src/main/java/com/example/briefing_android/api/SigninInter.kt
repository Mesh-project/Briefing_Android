package com.example.briefing_android.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SigninInter {
    //로그인 V
    @POST("/api/signin/")
    fun requestSignIn(
            @Body signinRequest: SigninRequest
    ): Call<SigninResponse>
}

//요청
data class SigninRequest(
    @SerializedName("user_id") //아이디
    val email : String,
    @SerializedName("user_pw") //비밀번호
    val pw : String
)

// 응답
data class SigninResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String
)