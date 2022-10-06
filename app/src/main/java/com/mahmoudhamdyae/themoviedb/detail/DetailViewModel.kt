package com.mahmoudhamdyae.themoviedb.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb.network.MovieNetwork

/**
 * The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [MovieNetwork].
 */
class DetailViewModel(movie: MovieNetwork, app: Application) : AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData<MovieNetwork>()
    val selectedProperty: LiveData<MovieNetwork>
        get() = _selectedProperty

    init {
        _selectedProperty.value = movie
    }
}