package com.mahmoudhamdyae.themoviedb1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.mahmoudhamdyae.themoviedb1.models.Movie
import com.mahmoudhamdyae.themoviedb1.room.MoviesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application): AndroidViewModel(application) {

    private val _movies = MutableStateFlow<List<Movie>>(listOf())
    val movies: MutableStateFlow<List<Movie>>
        get() = _movies

    private val _tvShows = MutableStateFlow<List<Movie>>(listOf())
    val tvShows: MutableStateFlow<List<Movie>>
        get() = _tvShows

    private val _test = MutableLiveData<String>()
    val test: LiveData<String>
        get() = _test


    private val db = Room.databaseBuilder(
    application,
    MoviesDatabase::class.java, "movies"
    ).build()
    private val dao = db.movieDao()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getFavourites()
    }

    fun getFavourites() {
        coroutineScope.launch {
            try {
                dao.getMovies().collect {
                    _movies.value = listOf()
                    _tvShows.value = listOf()
                    it.forEach {movie : Movie ->
                        if (movie.title != "") {
                            // Movie
                            _movies.value = _movies.value + movie
                        } else {
                            // TV Show
                            _tvShows.value = _tvShows.value + movie
                        }
                    }
                    _movies.value = _movies.value.reversed()
                    _tvShows.value = _tvShows.value.reversed()
                }
            } catch (e: Exception) {
                _test.value = e.toString()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}