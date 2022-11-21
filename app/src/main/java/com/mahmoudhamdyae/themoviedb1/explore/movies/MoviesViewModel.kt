package com.mahmoudhamdyae.themoviedb1.explore.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb1.MovieApiStatus
import com.mahmoudhamdyae.themoviedb1.database.network.Movie
import com.mahmoudhamdyae.themoviedb1.database.network.MovieApi
import kotlinx.coroutines.*

class MoviesViewModel(page: Int, application: Application) : AndroidViewModel(application) {

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()
    // The external immutable LiveData for the navigation property
    val navigateToSelectedMovie: MutableLiveData<Movie?>
        get() = _navigateToSelectedMovie

    // The external immutable LiveData for the request status
    private val _statusPopular = MutableLiveData<MovieApiStatus>()
    val statusPopular: LiveData<MovieApiStatus>
        get() = _statusPopular

    private val _statusTopRated = MutableLiveData<MovieApiStatus>()
    val statusTopRated: LiveData<MovieApiStatus>
        get() = _statusTopRated

    private val _moviesListPopular = MutableLiveData<List<Movie>>()
    val moviesListPopular: LiveData<List<Movie>>
        get() = _moviesListPopular

    private val _moviesListTopRated = MutableLiveData<List<Movie>>()
    val moviesListTopRated: LiveData<List<Movie>>
        get() = _moviesListTopRated

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMoviesPopular(page)
        getMoviesTopRated()
    }

    private fun getMoviesPopular(page : Int) {
        coroutineScope.launch {
            try {
                _statusPopular.value = MovieApiStatus.LOADING
                _moviesListPopular.value = MovieApi.retrofitService.getPopularMoviesAsync(page.toString()).await().results
                _statusPopular.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (_moviesListPopular.value.isNullOrEmpty())
                    _statusPopular.value = MovieApiStatus.ERROR
            }
        }
    }

    private fun getMoviesTopRated() {
        coroutineScope.launch {
            try {
                _statusTopRated.value = MovieApiStatus.LOADING
                _moviesListTopRated.value = MovieApi.retrofitService.getTopRatedMoviesAsync().await().results
                _statusTopRated.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (_moviesListTopRated.value.isNullOrEmpty())
                    _statusTopRated.value = MovieApiStatus.ERROR
            }
        }
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedMovie] [MutableLiveData]
     * @param movie The [Movie] that was clicked on.
     */
    fun displayPropertyDetails(movie: Movie) {
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