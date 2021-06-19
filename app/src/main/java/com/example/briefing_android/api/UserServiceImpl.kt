package com.example.briefing_android.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object UserServiceImpl {
    private const val BASE_URL = "http://52.79.55.149:8080"


    var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS)
        .writeTimeout(100, TimeUnit.SECONDS)
        .build()


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    var SignInService: SigninInter = retrofit.create(SigninInter::class.java)
    val SignUpService: SignupInter = retrofit.create(SignupInter::class.java)
    val AnalysisService: analysisInter = retrofit.create(analysisInter::class.java)
    val CommentService: CommentInter = retrofit.create(CommentInter::class.java)
    val HistoryService: HistoryInter = retrofit.create(HistoryInter::class.java)
}