package com.example.briefing_android.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//회원가입
interface SignupInter {
    @POST("/api/signup/")
    fun requestSignUp(
            @Body signupRequest: SignupRequest
    ): Call<SignupResponse>
}


data class SignupRequest(
        @SerializedName("user_id") //아이디
        val user_id : String,
        @SerializedName("user_pw") //비밀번호
        val user_pw : String
)

data class SignupResponse(
        @SerializedName("status")
        val status: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: signup_data
)

data class signup_data(
        @SerializedName("user_id") //아이디
        val user_id : String,
        @SerializedName("user_pw") //비밀번호
        val user_pw : String
)