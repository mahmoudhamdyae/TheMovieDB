package com.mahmoudhamdyae.themoviedb1.data.network

import com.mahmoudhamdyae.themoviedb1.BuildConfig
import com.mahmoudhamdyae.themoviedb1.data.models.NetworkMovieContainer
import com.mahmoudhamdyae.themoviedb1.data.models.NetworkReviewContainer
import com.mahmoudhamdyae.themoviedb1.data.models.NetworkTrailerContainer
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVShowsService {

    // https://api.themoviedb.org/3/discover/tv?api_key=API_KEY&sort_by=popularity.desc&page=1
    @GET("discover/tv?api_key=${BuildConfig.API_KEY}&sort_by=popularity.desc")
    fun getPopularTVShowsAsync(@Query("page") page: Int) : Deferred<NetworkMovieContainer>

    // https://api.themoviedb.org/3/movie/top_rated?api_key=$API_KEY
    @GET("tv/top_rated?api_key=${BuildConfig.API_KEY}")
    fun getTopRatedTVShowsAsync(@Query("page") page: Int) : Deferred<NetworkMovieContainer>

    // https://api.themoviedb.org/3/tv/760161/reviews?api_key=API_KEY
    @GET("tv/{movieId}/reviews?api_key=${BuildConfig.API_KEY}")
    fun getTVReviewsAsync(@Path("movieId") movieId: String): Deferred<NetworkReviewContainer>

    // https://api.themoviedb.org/3/tv/760161/videos?api_key=API_KEY
    @GET("tv/{movieId}/videos?api_key=${BuildConfig.API_KEY}")
    fun getTVTrailersAsync(@Path("movieId") movieId: String): Deferred<NetworkTrailerContainer>

    // https://api.themoviedb.org/3/search/tv?api_key=$API_KEY&query=dragon
    @GET("search/tv?api_key=${BuildConfig.API_KEY}")
    fun getTVShowSearchAsync(@Query("query") query : String): Deferred<NetworkMovieContainer>
}