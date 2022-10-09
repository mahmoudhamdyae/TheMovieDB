package com.mahmoudhamdyae.themoviedb.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Movie(
    val id: String,
    val title: String,
    val posterPath: String,
    val overview: String,
    val userRating: String,
    val releaseDate: String
) : Parcelable

class Review(
    val id: String,
    val author: String,
    val content: String
)

class Trailer(
    val id: String,
    val key: String
)