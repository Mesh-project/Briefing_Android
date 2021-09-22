package com.example.briefing_android.summary.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.briefing_android.summary.recyclerview_comment.CommentItem

class SharedServerModel:ViewModel() {
    var englishlist = MutableLiveData<ArrayList<CommentItem>>()
}