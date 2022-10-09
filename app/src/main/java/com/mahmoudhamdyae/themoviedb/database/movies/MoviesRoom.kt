package com.mahmoudhamdyae.themoviedb.database.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmoudhamdyae.themoviedb.domain.Movie

@Entity
class MovieRoom (
    @PrimaryKey
    val id: String,
    val title: String,
    val posterPath: String,
    val overview: String,
    val userRating: String,
    val releaseDate: String
)

fun List<MovieRoom>.asDomainModel() : List<Movie> {
    return map {
        Movie(
            id = it .id,
            title = it.title,
            posterPath = it.posterPath,
            overview = it.overview,
            userRating = it.userRating,
            releaseDate = it.releaseDate
        )
    }
}