package com.mahmoudhamdyae.themoviedb1.data.repository

import com.mahmoudhamdyae.themoviedb1.data.network.ApiService
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService
) {

    // Movies

    suspend fun getPopularMovies(page: Int) =
        apiService.getPopularMoviesAsync(page).await().results

    suspend fun getTopRatedMovies(page: Int) =
        apiService.getTopRatedMoviesAsync(page).await().results

    suspend fun getSearchedMovies(query: String) =
        apiService.getMovieSearchAsync(query).await().results

    suspend fun getReviews(movieId: String) =
        apiService.getReviewsAsync(movieId).await().results

    suspend fun getTrailers(movieId: String) =
        apiService.getTrailersAsync(movieId).await().results

    // TV Shows

    suspend fun getPopularTVShows(page: Int) =
        apiService.getPopularTVShowsAsync(page).await().results

    suspend fun getTopRatedTVShows(page: Int) =
        apiService.getTopRatedTVShowsAsync(page).await().results

    suspend fun getSearchedTVShows(query: String) =
        apiService.getTVShowSearchAsync(query).await().results

    suspend fun getTVReviews(movieId: String) =
        apiService.getTVReviewsAsync(movieId).await().results

    suspend fun getTVTrailers(movieId: String) =
        apiService.getTVTrailersAsync(movieId).await().results
}