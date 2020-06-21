package com.sample.userstory.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(): ViewModel() {
    val contentUrl = MutableLiveData<String>()

    fun loadUrl(url: String) {
        contentUrl.value = url
    }
}