package com.mahmoudhamdyae.themoviedb1.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
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
@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkMovieContainer(val results: List<Movie>) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
@Entity
data class Movie (
    @PrimaryKey
    val id: String,
    @Json(name = "title") val title: String = "", // Movie's Title
    @Json(name = "name") val name: String = "", // TV Show's Name
    val realName: String = title + name,
    @Json(name = "poster_path") val posterPath: String?,
    val overview: String,
    @Json(name = "vote_average") val userRating: String,
    @Json(name= "release_date") val releaseDate: String = ""
    ) : Parcelable
