package com.mahmoudhamdyae.themoviedb.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb.network.MovieNetwork

/**
 * Simple ViewModel factory that provides the MovieProperty and context to the ViewModel.
 */
class DetailViewModelFactory(
    private val movie: MovieNetwork,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(movie, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}