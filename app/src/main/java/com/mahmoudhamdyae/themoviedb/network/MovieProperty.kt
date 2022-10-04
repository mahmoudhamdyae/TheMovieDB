package com.mahmoudhamdyae.themoviedb.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * MovieHolder holds a list of Movies.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "results": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkMovieContainer(val results: List<MovieProperty>)

@JsonClass(generateAdapter = true)
data class MovieProperty (
    val id: String,
    val title: String,
    @Json(name = "poster_path") val posterPath: String,
    val overview: String,
    @Json(name = "vote_average") val userRating: String,
    @Json(name= "release_date") val releaseDate: String
    )