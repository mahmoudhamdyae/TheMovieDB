package com.mahmoudhamdyae.themoviedb.explore.tvshows

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TVShowsViewModelFactory(
    private val page: Int,
    private val application: Application
) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TVShowsViewModel::class.java)) {
            return TVShowsViewModel(page, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}