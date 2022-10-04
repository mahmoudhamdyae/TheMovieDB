package com.mahmoudhamdyae.themoviedb.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoviesViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val response: LiveData<String>
        get() = _response

    init {
        getMovies()
    }

    private fun getMovies() {
        _response.value = "Set the Mars API Response here!"
    }
}