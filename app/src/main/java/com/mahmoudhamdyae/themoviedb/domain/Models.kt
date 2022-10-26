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

@Parcelize
class TVShow(
    val id: String,
    val title: String,
    val posterPath: String,
    val overview: String,
    val userRating: String,
    val releaseDate: String
) : Parcelable