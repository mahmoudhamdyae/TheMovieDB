package com.mahmoudhamdyae.themoviedb1.repository

import com.mahmoudhamdyae.themoviedb1.network.MovieApi
import com.mahmoudhamdyae.themoviedb1.room.MovieDao

class Repository {

    suspend fun getPopularMovies(page: Int) =
        MovieApi.retrofitService.getPopularMoviesAsync(page.toString()).await().results

    suspend fun getTopRatedMovies() =
        MovieApi.retrofitService.getTopRatedMoviesAsync().await().results

    suspend fun getPopularTVShows(page: Int) =
        MovieApi.retrofitService.getPopularTVShowsAsync(page.toString()).await().results

    suspend fun getTopRatedTVShows() =
        MovieApi.retrofitService.getTopRatedTVShowsAsync().await().results

    suspend fun getSearchedMovies(query: String) =
        MovieApi.retrofitService.getMovieSearchAsync(query).await().results

    suspend fun getSearchedTVShows(query: String) =
        MovieApi.retrofitService.getTVShowSearchAsync(query).await().results

    suspend fun getReviews(movieId: String) =
        MovieApi.retrofitService.getReviewsAsync(movieId).await().results

    suspend fun getTrailers(movieId: String) =
        MovieApi.retrofitService.getTrailersAsync(movieId).await().results

    suspend fun getTVReviews(movieId: String) =
        MovieApi.retrofitService.getTVReviewsAsync(movieId).await().results

    suspend fun getTVTrailers(movieId: String) =
        MovieApi.retrofitService.getTVTrailersAsync(movieId).await().results

    fun getFavouriteMovies(dao: MovieDao) = dao.getMovies()
}