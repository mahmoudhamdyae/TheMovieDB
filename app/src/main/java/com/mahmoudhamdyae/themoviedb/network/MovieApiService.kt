package com.mahmoudhamdyae.themoviedb.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

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
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getPopularMovies] method
 */
interface MovieApiService {
    /**
     * Returns a Retrofit callback that delivers a [List] of [MovieProperty]
     * The @GET annotation indicates that the "getPopularMovies" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("discover/movie?api_key=$API_KEY&sort_by=popularity.desc")
    fun getPopularMovies(): Call<NetworkMovieContainer>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MovieApi {
    val retrofitService : MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java) }
}