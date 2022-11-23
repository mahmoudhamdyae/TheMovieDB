package com.mahmoudhamdyae.themoviedb1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb1.data.models.Movie

class AllViewModel(movies: List<Movie>, application: Application) : AndroidViewModel(application) {

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList : LiveData<List<Movie>>
        get() = _moviesList

    init {
        _moviesList.value = movies
    }
}