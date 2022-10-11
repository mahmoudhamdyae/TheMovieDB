package com.mahmoudhamdyae.themoviedb.network

import com.mahmoudhamdyae.themoviedb.database.movies.MovieRoom
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
data class NetworkMovieContainer(val results: List<MovieNetwork>)

@JsonClass(generateAdapter = true)
data class MovieNetwork (
    val id: String,
    val title: String,
    @Json(name = "poster_path") val posterPath: String,
    val overview: String,
    @Json(name = "vote_average") val userRating: String,
    @Json(name= "release_date") val releaseDate: String
    )

fun NetworkMovieContainer.asDatabaseModel() : Array<MovieRoom> {
    return results.map {
        MovieRoom(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            overview = it.overview,
            userRating = it.userRating,
            releaseDate = it.releaseDate
        )
    }.toTypedArray()
}

// Reviews

@JsonClass(generateAdapter = true)
data class NetworkReviewContainer(val results: List<NetworkReview>)

@JsonClass(generateAdapter = true)
data class NetworkReview(
    val id: String,
    val author: String,
    val content: String
)

// Trailers

@JsonClass(generateAdapter = true)
data class NetworkTrailerContainer(val results: List<NetworkTrailer>)

@JsonClass(generateAdapter = true)
data class NetworkTrailer(
    val id: String,
    val name: String,
    val key: String,
    val site: String
)
