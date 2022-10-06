package com.mahmoudhamdyae.themoviedb.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb.network.MovieProperty

/**
 * The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [MovieProperty].
 */
class DetailViewModel(movie: MovieProperty, app: Application) : AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData<MovieProperty>()
    val selectedProperty: LiveData<MovieProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = movie
    }
}