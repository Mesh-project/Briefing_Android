package com.example.briefing_android.summary

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.briefing_android.api.korean_data
import com.example.briefing_android.summary.recyclerview_comment.CommentItem

class SharedPiechartModel : ViewModel(){
    // 댓글 수 저장
    var koreansize = MutableLiveData<Int>()
    var englishsize = MutableLiveData<Int>()
    var etcsize = MutableLiveData<Int>()
    var positivesize = MutableLiveData<Int>()
    var negativesize = MutableLiveData<Int>()
    //var negativesize = MutableLiveData<Int>()

    var koreanlist = MutableLiveData<ArrayList<CommentItem>>()
    var englishlist = MutableLiveData<CommentItem>()
    var etclist = MutableLiveData<CommentItem>()
}