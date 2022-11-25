package com.mahmoudhamdyae.themoviedb1.data.repository

import com.mahmoudhamdyae.themoviedb1.data.network.TVShowsService
import javax.inject.Inject

class TVShowsRepository @Inject constructor(
    private val tvShowsService: TVShowsService
) {

    // Retrofit

    suspend fun getPopularTVShows(page: Int) =
        tvShowsService.getPopularTVShowsAsync(page).await().results

    suspend fun getTopRatedTVShows(page: Int) =
        tvShowsService.getTopRatedTVShowsAsync(page).await().results

    suspend fun getSearchedTVShows(query: String) =
        tvShowsService.getTVShowSearchAsync(query).await().results

    suspend fun getTVReviews(movieId: String) =
        tvShowsService.getTVReviewsAsync(movieId).await().results

    suspend fun getTVTrailers(movieId: String) =
        tvShowsService.getTVTrailersAsync(movieId).await().results
}