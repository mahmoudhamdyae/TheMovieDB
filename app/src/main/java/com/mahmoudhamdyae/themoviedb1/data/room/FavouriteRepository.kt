package com.mahmoudhamdyae.themoviedb1.data.room

import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import javax.inject.Inject

class FavouriteRepository @Inject constructor(
    private val dao: MovieDao
) {

    fun getFavouriteMovies() = dao.getMovies()

    fun insertFavouriteMovie(movie: Movie) = dao.insertMovie(movie)

    fun deleteFavouriteMovie(movie: Movie) = dao.deleteMovie(movie)
}