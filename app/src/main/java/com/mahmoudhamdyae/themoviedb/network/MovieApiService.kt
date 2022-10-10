package com.mahmoudhamdyae.themoviedb.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.themoviedb.org/3/"

// API Key
private const val API_KEY = "308fc9935783b6199369f60243c21395"

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

/**
 * A public interface that exposes the [getPopularMoviesAsync] method
 */
interface MovieApiService {
    /**
     * Returns a Coroutine [List] of [MovieNetwork] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "getPopularMovies" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("discover/movie?api_key=$API_KEY&sort_by=popularity.desc")
    fun getPopularMoviesAsync(): Deferred<NetworkMovieContainer>

    @GET("movie/{movieId}/reviews?api_key=308fc9935783b6199369f60243c21395")
    fun getReviewsAsync(@Path("movieId") movieId: String): Deferred<NetworkReviewContainer>

    @GET("movie/{movieId}/videos?api_key=308fc9935783b6199369f60243c21395")
    fun getTrailersAsync(@Path("movieId") movieId: String): Deferred<NetworkTrailerContainer>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MovieApi {
    val retrofitService : MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java) }
}