package com.mahmoudhamdyae.themoviedb.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.mahmoudhamdyae.themoviedb.database.local.MoviesDatabase
import com.mahmoudhamdyae.themoviedb.database.network.Movie
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class FavouriteViewModel(application: Application): AndroidViewModel(application) {

    private val _movies = MutableStateFlow<List<Movie>>(listOf())
    val movies: MutableStateFlow<List<Movie>>
        get() = _movies

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
                    _movies.value = it
                    _movies.value = _movies.value.reversed()
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