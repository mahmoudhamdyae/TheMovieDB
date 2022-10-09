package com.mahmoudhamdyae.themoviedb.detail.summary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SummaryViewModel(overview: String, application: Application) : AndroidViewModel(application) {

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String>
        get() = _overview

    init {
        _overview.value = overview
    }
}