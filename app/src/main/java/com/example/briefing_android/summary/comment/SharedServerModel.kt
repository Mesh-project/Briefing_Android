package com.example.briefing_android.summary.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedServerModel:ViewModel() {
    // 긍정 부정
    var positivesize = MutableLiveData<Int>()
    var negativesize = MutableLiveData<Int>()

    // 언어별
    var koreansize = MutableLiveData<Int>()
    var englishsize = MutableLiveData<Int>()
    var etcsize = MutableLiveData<Int>()
}