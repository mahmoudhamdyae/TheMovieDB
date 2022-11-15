package com.mahmoudhamdyae.themoviedb.database.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

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
data class NetworkMovieContainer(val results: List<Movie>)

@Parcelize
@JsonClass(generateAdapter = true)
data class Movie (
    val id: String,
    @Json(name = "title") val title: String = "", // Movie's Title
    @Json(name = "name") val name: String = "", // TV Show's Name
    @Json(name = "poster_path") val posterPath: String,
    val overview: String,
    @Json(name = "vote_average") val userRating: String,
    @Json(name= "release_date") val releaseDate: String = ""
    ) : Parcelable

@JsonClass(generateAdapter = true)
data class NetworkTVShowContainer(val results: List<Movie>)

// Reviews

@JsonClass(generateAdapter = true)
data class NetworkReviewContainer(val results: List<Review>)

@JsonClass(generateAdapter = true)
data class Review(
    val id: String,
    val author: String,
    val content: String
)

// Trailers

@JsonClass(generateAdapter = true)
data class NetworkTrailerContainer(val results: List<Trailer>)

@JsonClass(generateAdapter = true)
data class Trailer(
    val id: String,
    val name: String,
    val key: String,
    val site: String
)

//@JsonClass(generateAdapter = true)
//data class GuestSession(
//    val success: Boolean,
//    @Json(name = "guest_session_id") val guestSessionId: String
//)

//@JsonClass(generateAdapter = true)
//data class RequestToken(
//    val success: Boolean,
//    @Json(name = "request_token") val requestToken: String
//)

//@JsonClass(generateAdapter = true)
//data class Session(
//    val success: Boolean,
//    @Json(name = "session_id") val sessionId: String
//)
