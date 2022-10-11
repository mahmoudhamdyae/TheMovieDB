package com.mahmoudhamdyae.themoviedb.detail.trailers

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TrailersViewModelFactory(
    private val movieId: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrailersViewModel::class.java)) {
            return TrailersViewModel(movieId, application) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}