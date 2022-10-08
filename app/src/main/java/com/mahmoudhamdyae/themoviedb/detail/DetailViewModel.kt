package com.mahmoudhamdyae.themoviedb.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb.domain.Movie

class DetailViewModel(movie: Movie, app: Application) : AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData<Movie>()
    val selectedProperty: LiveData<Movie>
        get() = _selectedProperty

    init {
        _selectedProperty.value = movie
    }
}