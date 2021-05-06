package com.example.briefing_android.api
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//fun abc(a : Int, b : Int, c : Int){
//
//}

fun <T> Call<T>.safeEnqueue(
    onError: (Throwable) -> Unit = onErrorStub,
    onResponse: (res: Response<T>) -> Unit = {}
) {
    this.enqueue(
        object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.v("onFailure","실패")
                onError(t)
            }

            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                Log.v("onResponse","성공")
                onResponse(response)
            }
        }
    )
}

internal val onErrorStub: (Throwable) -> Unit = {
    Log.e("safeEnqueue", "network error : $it")
}
