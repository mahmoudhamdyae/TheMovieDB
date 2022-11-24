package com.mahmoudhamdyae.themoviedb1.viewmodels

import androidx.lifecycle.*

class SummaryViewModel(
    overview: String
) : ViewModel() {

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String>
        get() = _overview

    init {
        _overview.value = overview
    }

    /**
     * Simple ViewModel factory that provides the Summary and context to the ViewModel.
     */
    class SummaryViewModelFactory(
        private val overview: String
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SummaryViewModel::class.java)) {
                return SummaryViewModel(overview) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}