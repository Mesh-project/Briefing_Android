package com.example.briefing_android.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object UserServiceImpl {
    private const val BASE_URL = "http://52.79.55.149:8080"

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    var SignInService: SigninInter = retrofit.create(SigninInter::class.java)
    val SignUpService: SignupInter = retrofit.create(SignupInter::class.java)
    val AnalysisService: analysisInter = retrofit.create(analysisInter::class.java)
    val CommentService: CommentInter = retrofit.create(CommentInter::class.java)
}