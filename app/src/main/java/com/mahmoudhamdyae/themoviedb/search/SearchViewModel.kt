package com.mahmoudhamdyae.themoviedb.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _tvShows = MutableLiveData<List<Movie>>()
    val tvShows : LiveData<List<Movie>>
        get() = _tvShows

    private val _error = MutableLiveData<String>()
    val error : LiveData<String>
        get() = _error

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getSearchedMovies(query: String) {
        coroutineScope.launch {
            try {
                _movies.value = listOf()
                _movies.value = MovieApi.retrofitService.getMovieSearchAsync(query).await().results
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }

    fun getSearchedTVShows(query: String) {
        coroutineScope.launch {
            try {
                _tvShows.value = listOf()
                _tvShows.value = MovieApi.retrofitService.getTVShowSearchAsync(query).await().results
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}