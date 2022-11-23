package com.mahmoudhamdyae.themoviedb1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.repository.Repository
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
                _movies.value = Repository().getSearchedMovies(query)
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }

    fun getSearchedTVShows(query: String) {
        coroutineScope.launch {
            try {
                _tvShows.value = listOf()
                _tvShows.value = Repository().getSearchedTVShows(query)
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