package com.mahmoudhamdyae.themoviedb.database.tvshows

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmoudhamdyae.themoviedb.domain.TVShow

@Entity
class TVShowsRoom (
    @PrimaryKey
    val id: String,
    val title: String,
    val posterPath: String,
    val overview: String,
    val userRating: String,
    val releaseDate: String
    )

fun List<TVShowsRoom>.asDomainModel() : List<TVShow> {
    return map {
        TVShow(
            id = it .id,
            title = it.title,
            posterPath = it.posterPath,
            overview = it.overview,
            userRating = it.userRating,
            releaseDate = it.releaseDate
        )
    }
}