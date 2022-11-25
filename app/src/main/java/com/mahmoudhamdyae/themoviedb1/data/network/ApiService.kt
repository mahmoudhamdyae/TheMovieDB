package com.mahmoudhamdyae.themoviedb1.data.network

import com.mahmoudhamdyae.themoviedb1.BuildConfig.API_KEY
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.models.NetworkMovieContainer
import com.mahmoudhamdyae.themoviedb1.data.models.NetworkReviewContainer
import com.mahmoudhamdyae.themoviedb1.data.models.NetworkTrailerContainer
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /**
     * Returns a Coroutine [List] of [Movie] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "getPopularMovies" endpoint will be requested with the GET
     * HTTP method
     */
    // https://api.themoviedb.org/3/discover/movie?api_key=API_KEY&sort_by=popularity.desc&page=1
    @GET("discover/movie?api_key=$API_KEY&sort_by=popularity.desc")
    fun getPopularMoviesAsync(): Deferred<NetworkMovieContainer>

    // https://api.themoviedb.org/3/movie/top_rated?api_key=$API_KEY
    @GET("movie/top_rated?api_key=$API_KEY")
    fun getTopRatedMoviesAsync(): Deferred<NetworkMovieContainer>

    // https://api.themoviedb.org/3/movie/760161/reviews?api_key=API_KEY
    @GET("movie/{movieId}/reviews?api_key=$API_KEY")
    fun getReviewsAsync(@Path("movieId") movieId: String): Deferred<NetworkReviewContainer>

    // https://api.themoviedb.org/3/movie/760161/videos?api_key=API_KEY
    @GET("movie/{movieId}/videos?api_key=$API_KEY")
    fun getTrailersAsync(@Path("movieId") movieId: String): Deferred<NetworkTrailerContainer>

    // TV Shows

    // https://api.themoviedb.org/3/discover/tv?api_key=API_KEY&sort_by=popularity.desc&page=1
    @GET("discover/tv?api_key=$API_KEY&sort_by=popularity.desc")
    fun getPopularTVShowsAsync() : Deferred<NetworkMovieContainer>

    // https://api.themoviedb.org/3/movie/top_rated?api_key=$API_KEY
    @GET("tv/top_rated?api_key=$API_KEY")
    fun getTopRatedTVShowsAsync() : Deferred<NetworkMovieContainer>

    // https://api.themoviedb.org/3/tv/760161/reviews?api_key=API_KEY
    @GET("tv/{movieId}/reviews?api_key=$API_KEY")
    fun getTVReviewsAsync(@Path("movieId") movieId: String): Deferred<NetworkReviewContainer>

    // https://api.themoviedb.org/3/tv/760161/videos?api_key=API_KEY
    @GET("tv/{movieId}/videos?api_key=$API_KEY")
    fun getTVTrailersAsync(@Path("movieId") movieId: String): Deferred<NetworkTrailerContainer>

    // https://api.themoviedb.org/3/search/movie?api_key=$API_KEY&query=dragon
    @GET("search/movie?api_key=$API_KEY")
    fun getMovieSearchAsync(@Query("query") query : String): Deferred<NetworkMovieContainer>

    // https://api.themoviedb.org/3/search/tv?api_key=$API_KEY&query=dragon
    @GET("search/tv?api_key=$API_KEY")
    fun getTVShowSearchAsync(@Query("query") query : String): Deferred<NetworkMovieContainer>

    // Authentication

    // https://api.themoviedb.org/3/authentication/guest_session/new?api_key=API_KEY
//    @GET("authentication/guest_session/new?api_key=$API_KEY")
//    fun createGuestSessionAsync(): Deferred<GuestSession>

    // https://api.themoviedb.org/3/authentication/token/new?api_key=API_KEY
//    @GET("authentication/token/new?api_key=$API_KEY")
//    fun createRequestTokenAsync(): Deferred<RequestToken>

    // https://api.themoviedb.org/3/authentication/session/new?api_key=API_KEY
//    @POST("authentication/session/new?api_key=$API_KEY")
//    fun createSessionAsync(): Deferred<Session>
}