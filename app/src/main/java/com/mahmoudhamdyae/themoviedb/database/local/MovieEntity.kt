package com.mahmoudhamdyae.themoviedb.database.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val title: String = "", // Movie's Title
    val name: String = "", // TV Show's Name
    val realName: String = title + name,
    val posterPath: String,
    val overview: String,
    val userRating: String,
    val releaseDate: String = ""
)
