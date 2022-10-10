package com.mahmoudhamdyae.themoviedb.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb.database.MovieRepository
import com.mahmoudhamdyae.themoviedb.database.getDatabase
import com.mahmoudhamdyae.themoviedb.domain.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MovieApiStatus { LOADING, ERROR, DONE }

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

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

    private val database =
        getDatabase(application)
    private val moviesRepository =
        MovieRepository(database)

    val moviesList = moviesRepository.movies

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getMovies()
    }

    private fun getMovies() {
        coroutineScope.launch {
            try {
                _status.value = MovieApiStatus.LOADING
                moviesRepository.refreshMovies()
//                if (moviesList.value?.isEmpty()!!)
//                    _status.value = MovieApiStatus.ERROR
//                else {
                    _status.value = MovieApiStatus.DONE
//                    _toastNetwork.value = R.string.network_not_available.toString()
//                }
            } catch (e: Exception) {
                _status.value = MovieApiStatus.ERROR
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