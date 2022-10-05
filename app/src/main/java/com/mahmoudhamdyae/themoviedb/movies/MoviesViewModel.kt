package com.mahmoudhamdyae.themoviedb.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoudhamdyae.themoviedb.network.MovieApi
import com.mahmoudhamdyae.themoviedb.network.MovieProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val _moviesList = MutableLiveData<List<MovieProperty>>()
    val moviesList: LiveData<List<MovieProperty>>
        get() = _moviesList

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedMovie = MutableLiveData<MovieProperty>()
    // The external immutable LiveData for the navigation property
    val navigateToSelectedMovie: LiveData<MovieProperty>
        get() = _navigateToSelectedMovie

    // Handle Exception
    private val _toastShow = MutableLiveData<String>()
    val toastShow: LiveData<String>
        get() = _toastShow

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getMovies()
    }

    private fun getMovies() {
        coroutineScope.launch {
            val getMoviesDeferred = MovieApi.retrofitService.getPopularMoviesAsync()
            try {
                val listResults = getMoviesDeferred.await()
                _moviesList.value = listResults.results
            } catch (e: Exception) {
                _toastShow.value = e.message
            }
        }
    }

    fun toastShowCleared() {
        _toastShow.value = null
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedMovie] [MutableLiveData]
     * @param movie The [MovieProperty] that was clicked on.
     */
    fun displayPropertyDetails(movie: MovieProperty) {
        _navigateToSelectedMovie.value = movie
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}