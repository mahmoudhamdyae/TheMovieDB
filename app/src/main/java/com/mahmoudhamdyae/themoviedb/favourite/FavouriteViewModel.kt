package com.mahmoudhamdyae.themoviedb.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.bumptech.glide.Glide.init
import com.mahmoudhamdyae.themoviedb.database.local.MoviesDatabase
import com.mahmoudhamdyae.themoviedb.database.network.Movie
import com.mahmoudhamdyae.themoviedb.database.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application): AndroidViewModel(application) {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val db = Room.databaseBuilder(
    application,
    MoviesDatabase::class.java, "database-name"
    ).build()
    private val dao = db.movieDao()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        coroutineScope.launch {
//            _movies.value = dao.getMovies()
            _movies.value = MovieApi.retrofitService.getTopRatedMoviesAsync().await().results
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}