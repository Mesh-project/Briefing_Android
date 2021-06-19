package com.example.briefing_android.summary

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedPiechartModel : ViewModel(){
    // 댓글 수 저장
    var koreansize = MutableLiveData<Int>()
    var englishsize = MutableLiveData<Int>()
    var etcsize = MutableLiveData<Int>()
    var positivesize = MutableLiveData<Int>()
    //var negativesize = MutableLiveData<Int>()

}