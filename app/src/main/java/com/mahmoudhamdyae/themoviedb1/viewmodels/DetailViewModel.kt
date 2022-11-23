package com.mahmoudhamdyae.themoviedb1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.mahmoudhamdyae.themoviedb1.models.Movie
import com.mahmoudhamdyae.themoviedb1.room.MoviesDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

class DetailViewModel(movie: Movie, app: Application) : AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData<Movie>()
    val selectedProperty: LiveData<Movie>
        get() = _selectedProperty

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val db = Room.databaseBuilder(
        app,
        MoviesDatabase::class.java, "movies"
    ).build()
    private val dao = db.movieDao()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _selectedProperty.value = movie
    }

    fun isFavourite(movie: Movie): Boolean {
        var ret: Boolean
        runBlocking {
            ret = dao.getMovies().first().contains(movie)
        }
        return ret
    }

    fun delMovie(movie: Movie) {
        coroutineScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    dao.deleteMovie(movie)
                }
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }

    fun insertMovie(movie: Movie) {
        coroutineScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    dao.insertMovie(movie)
                }
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