package com.mahmoudhamdyae.themoviedb1.data.repository

import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.network.ApiService
import com.mahmoudhamdyae.themoviedb1.data.room.MovieDao
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val dao: MovieDao
) {

    suspend fun getPopularMovies() =
        apiService.getPopularMoviesAsync().await().results

    suspend fun getTopRatedMovies() =
        apiService.getTopRatedMoviesAsync().await().results

    suspend fun getPopularTVShows() =
        apiService.getPopularTVShowsAsync().await().results

    suspend fun getTopRatedTVShows() =
        apiService.getTopRatedTVShowsAsync().await().results

    suspend fun getSearchedMovies(query: String) =
        apiService.getMovieSearchAsync(query).await().results

    suspend fun getSearchedTVShows(query: String) =
        apiService.getTVShowSearchAsync(query).await().results

    suspend fun getReviews(movieId: String) =
        apiService.getReviewsAsync(movieId).await().results

    suspend fun getTrailers(movieId: String) =
        apiService.getTrailersAsync(movieId).await().results

    suspend fun getTVReviews(movieId: String) =
        apiService.getTVReviewsAsync(movieId).await().results

    suspend fun getTVTrailers(movieId: String) =
        apiService.getTVTrailersAsync(movieId).await().results

    fun getFavouriteMovies() = dao.getMovies()

    fun insertFavouriteMovie(movie: Movie) = dao.insertMovie(movie)

    fun deleteFavouriteMovie(movie: Movie) = dao.deleteMovie(movie)
}