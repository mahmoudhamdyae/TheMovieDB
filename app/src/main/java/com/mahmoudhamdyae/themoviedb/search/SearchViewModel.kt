package com.mahmoudhamdyae.themoviedb.search

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb.MovieApiStatus
import com.mahmoudhamdyae.themoviedb.database.network.Movie
import com.mahmoudhamdyae.themoviedb.database.network.MovieApi
import com.mahmoudhamdyae.themoviedb.database.network.NetworkMovieContainer
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

    private val _moviesContainer = MutableLiveData<NetworkMovieContainer>()
    val moviesContainer : LiveData<NetworkMovieContainer>
        get() = _moviesContainer

    private val _tvShowsContainer = MutableLiveData<NetworkMovieContainer>()
    val tvShowsContainer : LiveData<NetworkMovieContainer>
        get() = _tvShowsContainer

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getSearchedMovies(query: String) {
        coroutineScope.launch {
            try {
                _moviesContainer.value = NetworkMovieContainer(listOf())
                _movies.value = listOf()
                _moviesContainer.value = MovieApi.retrofitService.getMovieSearchAsync(query).await()
                _movies.value = MovieApi.retrofitService.getMovieSearchAsync(query).await().results
            } catch (e: Exception) {
                if (_movies.value.isNullOrEmpty()) {
                }
            }
        }
    }

    fun getSearchedTVShows(query: String) {
        coroutineScope.launch {
            try {
                _tvShowsContainer.value = NetworkMovieContainer(listOf())
                _tvShows.value = listOf()
                _tvShowsContainer.value = MovieApi.retrofitService.getTVShowSearchAsync(query).await()
                _tvShows.value = MovieApi.retrofitService.getTVShowSearchAsync(query).await().results
            } catch (e: Exception) {
                if (_tvShows.value.isNullOrEmpty()) {
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}