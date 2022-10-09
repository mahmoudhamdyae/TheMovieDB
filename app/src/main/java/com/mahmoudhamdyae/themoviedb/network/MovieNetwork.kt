package com.mahmoudhamdyae.themoviedb.network

import com.mahmoudhamdyae.themoviedb.database.movies.MovieRoom
import com.mahmoudhamdyae.themoviedb.database.reviews.ReviewsRoom
import com.mahmoudhamdyae.themoviedb.database.trailers.TrailersRoom
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

fun NetworkReviewContainer.asDatabaseModel() : Array<ReviewsRoom> {
    return results.map {
        ReviewsRoom(
            id = it.id,
            author = it.author,
            content = it.content
        )
    }.toTypedArray()
}

// Trailers

@JsonClass(generateAdapter = true)
data class NetworkTrailerContainer(val results: List<NetworkTrailer>)

@JsonClass(generateAdapter = true)
data class NetworkTrailer(
    val id: String,
    val key: String
)

fun NetworkTrailerContainer.asDatabaseModel() : Array<TrailersRoom> {
    return results.map {
        TrailersRoom(
            id = it.id,
            key = it.key
        )
    }.toTypedArray()
}