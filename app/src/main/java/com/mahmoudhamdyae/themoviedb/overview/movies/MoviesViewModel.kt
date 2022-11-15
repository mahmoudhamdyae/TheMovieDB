package com.mahmoudhamdyae.themoviedb.overview.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb.MovieApiStatus
import com.mahmoudhamdyae.themoviedb.database.network.Movie
import com.mahmoudhamdyae.themoviedb.database.network.MovieApi
import kotlinx.coroutines.*

class MoviesViewModel(page: Int, application: Application) : AndroidViewModel(application) {

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()
    // The external immutable LiveData for the navigation property
    val navigateToSelectedMovie: MutableLiveData<Movie?>
        get() = _navigateToSelectedMovie

    // The external immutable LiveData for the request status
    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus>
        get() = _status

    // Handle Network is not available
//    private val _toastNetwork = MutableLiveData<String?>()
//    val toastNetwork: LiveData<String?>
//        get() = _toastNetwork

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getMovies(page)
    }

    private fun getMovies(page : Int) {
        coroutineScope.launch {
            try {
                _status.value = MovieApiStatus.LOADING
                _moviesList.value = MovieApi.retrofitService.getPopularMoviesAsync(page.toString()).await().results
                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (_moviesList.value.isNullOrEmpty())
                    _status.value = MovieApiStatus.ERROR
//                else
//                    _toastNetwork.value = R.string.network_not_available.toString()
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

//    fun clearToast() {
//        _toastNetwork.value = null
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}