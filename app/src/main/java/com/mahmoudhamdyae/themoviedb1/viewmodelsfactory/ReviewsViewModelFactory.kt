package com.mahmoudhamdyae.themoviedb1.viewmodelsfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb1.viewmodels.ReviewsViewModel

class ReviewsViewModelFactory(
    private val movieID: String,
    private val isMovie: Boolean,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewsViewModel::class.java)) {
            return ReviewsViewModel(movieID, isMovie, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}