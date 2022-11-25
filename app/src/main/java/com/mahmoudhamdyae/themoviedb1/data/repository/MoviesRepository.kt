package com.mahmoudhamdyae.themoviedb1.data.repository

import com.mahmoudhamdyae.themoviedb1.data.network.MoviesService
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesService: MoviesService
) {
    suspend fun getPopularMovies() =
        moviesService.getPopularMoviesAsync().await().results

    suspend fun getTopRatedMovies() =
        moviesService.getTopRatedMoviesAsync().await().results

    suspend fun getSearchedMovies(query: String) =
        moviesService.getMovieSearchAsync(query).await().results

    suspend fun getReviews(movieId: String) =
        moviesService.getReviewsAsync(movieId).await().results

    suspend fun getTrailers(movieId: String) =
        moviesService.getTrailersAsync(movieId).await().results
}