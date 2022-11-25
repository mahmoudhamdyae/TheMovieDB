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

interface MoviesService {
    /**
     * Returns a Coroutine [List] of [Movie] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "getPopularMovies" endpoint will be requested with the GET
     * HTTP method
     */
    // https://api.themoviedb.org/3/discover/movie?api_key=API_KEY&sort_by=popularity.desc&page=1
    @GET("discover/movie?api_key=$API_KEY&sort_by=popularity.desc")
    fun getPopularMoviesAsync(@Query("page") page: Int): Deferred<NetworkMovieContainer>

    // https://api.themoviedb.org/3/movie/top_rated?api_key=$API_KEY
    @GET("movie/top_rated?api_key=$API_KEY")
    fun getTopRatedMoviesAsync(@Query("page") page: Int): Deferred<NetworkMovieContainer>

    // https://api.themoviedb.org/3/movie/760161/reviews?api_key=API_KEY
    @GET("movie/{movieId}/reviews?api_key=$API_KEY")
    fun getReviewsAsync(@Path("movieId") movieId: String): Deferred<NetworkReviewContainer>

    // https://api.themoviedb.org/3/movie/760161/videos?api_key=API_KEY
    @GET("movie/{movieId}/videos?api_key=$API_KEY")
    fun getTrailersAsync(@Path("movieId") movieId: String): Deferred<NetworkTrailerContainer>

    // https://api.themoviedb.org/3/search/movie?api_key=$API_KEY&query=dragon
    @GET("search/movie?api_key=$API_KEY")
    fun getMovieSearchAsync(@Query("query") query : String): Deferred<NetworkMovieContainer>
}