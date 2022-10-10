package com.mahmoudhamdyae.themoviedb.detail.reviews

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReviewsViewModelFactory(
    private val movieID: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewsViewModel::class.java)) {
            return ReviewsViewModel(movieID, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}