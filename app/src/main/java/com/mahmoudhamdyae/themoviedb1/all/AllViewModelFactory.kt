package com.mahmoudhamdyae.themoviedb1.all

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb1.database.network.Movie

class AllViewModelFactory (
    private val movies: List<Movie>,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllViewModel::class.java)) {
            return AllViewModel(movies, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}