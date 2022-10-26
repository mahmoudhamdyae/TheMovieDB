package com.mahmoudhamdyae.themoviedb.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mahmoudhamdyae.themoviedb.database.movies.asDomainModel
import com.mahmoudhamdyae.themoviedb.database.tvshows.asDomainModel
import com.mahmoudhamdyae.themoviedb.domain.Movie
import com.mahmoudhamdyae.themoviedb.domain.TVShow
import com.mahmoudhamdyae.themoviedb.network.MovieApi
import com.mahmoudhamdyae.themoviedb.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: MoviesDatabase) {

    /**
     * A playlist of movies that can be shown on the screen.
     */
    val movies: LiveData<List<Movie>> =
        Transformations.map(database.movieDao.getMovies()) {
            it.asDomainModel()
        }

    val tvShows: LiveData<List<TVShow>> =
        Transformations.map(database.tvShowsDao.getTVShows()) {
            it.asDomainModel()
        }

    /**
     * Refresh the movies stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the movies for use, observe [movies]
     */
    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val moviesList = MovieApi.retrofitService.getPopularMoviesAsync().await()
            val moviesDao = database.movieDao
            moviesDao.clear()
            moviesDao.insertAll(*moviesList.asDatabaseModel())
        }
    }

    suspend fun refreshTVShows() {
        withContext(Dispatchers.IO) {
            val tvShowsList = MovieApi.retrofitService.getPopularTVShowsAsync().await()
            val tvShowsDao = database.tvShowsDao
            tvShowsDao.clear()
            tvShowsDao.insertAll(*tvShowsList.asDatabaseModel())
        }
    }
}