package com.mahmoudhamdyae.themoviedb.search

import android.app.Application
import android.os.Build.VERSION_CODES.M
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide.init
import com.mahmoudhamdyae.themoviedb.MovieApiStatus
import com.mahmoudhamdyae.themoviedb.database.network.Movie
import com.mahmoudhamdyae.themoviedb.database.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies : LiveData<List<Movie>>
        get() = _movies

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getSearchedMovies()
    }

    private fun getSearchedMovies() {
        coroutineScope.launch {
            try {
//                _statusTopRated.value = MovieApiStatus.LOADING

                _movies.value = MovieApi.retrofitService.getTopRatedTVShowsAsync().await().results

//                _statusTopRated.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (_movies.value.isNullOrEmpty()) {
//                    _statusTopRated.value = MovieApiStatus.ERROR
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}