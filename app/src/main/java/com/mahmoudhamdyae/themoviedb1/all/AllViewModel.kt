package com.mahmoudhamdyae.themoviedb1.all

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb1.database.network.Movie

class AllViewModel(movies: List<Movie>, application: Application) : AndroidViewModel(application) {

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList : LiveData<List<Movie>>
        get() = _moviesList

    init {
        _moviesList.value = movies
    }
}