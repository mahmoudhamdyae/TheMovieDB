package com.mahmoudhamdyae.themoviedb.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mahmoudhamdyae.themoviedb.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.themoviedb.org/3/"

// API Key
private const val API_KEY = BuildConfig.API_KEY

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object pointing to the desired URL
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService {
    /**
     * Returns a Coroutine [List] of [MovieNetwork] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "getPopularMovies" endpoint will be requested with the GET
     * HTTP method
     */
    // https://api.themoviedb.org/3/discover/movie?api_key=API_KEY&sort_by=popularity.desc
    @GET("discover/movie?api_key=$API_KEY&sort_by=popularity.desc")
    fun getPopularMoviesAsync(): Deferred<NetworkMovieContainer>

    // https://api.themoviedb.org/3/movie/760161/reviews?api_key=API_KEY
    @GET("movie/{movieId}/reviews?api_key=$API_KEY")
    fun getReviewsAsync(@Path("movieId") movieId: String): Deferred<NetworkReviewContainer>

    // https://api.themoviedb.org/3/movie/760161/videos?api_key=API_KEY
    @GET("movie/{movieId}/videos?api_key=$API_KEY")
    fun getTrailersAsync(@Path("movieId") movieId: String): Deferred<NetworkTrailerContainer>

    // TV Shows

    // https://api.themoviedb.org/3/discover/tv?api_key=API_KEY&sort_by=popularity.desc
    @GET("discover/tv?api_key=$API_KEY&sort_by=popularity.desc")
    fun getPopularTVShowsAsync() : Deferred<NetworkTVShowContainer>

    // https://api.themoviedb.org/3/tv/760161/reviews?api_key=API_KEY
    @GET("tv/{movieId}/reviews?api_key=$API_KEY")
    fun getTVReviewsAsync(@Path("movieId") movieId: String): Deferred<NetworkReviewContainer>

    // https://api.themoviedb.org/3/tv/760161/videos?api_key=API_KEY
    @GET("tv/{movieId}/videos?api_key=$API_KEY")
    fun getTVTrailersAsync(@Path("movieId") movieId: String): Deferred<NetworkTrailerContainer>

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

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MovieApi {
    val retrofitService : MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java) }
}