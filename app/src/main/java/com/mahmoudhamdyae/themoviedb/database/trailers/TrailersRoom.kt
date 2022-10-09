package com.mahmoudhamdyae.themoviedb.database.trailers

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmoudhamdyae.themoviedb.domain.Trailer

@Entity
class TrailersRoom (
    @PrimaryKey
    val id: String,
    val key: String
)

fun List<TrailersRoom>.asDomainModel() : List<Trailer> {
    return map {
        Trailer(
            id = it.id,
            key = it.key
        )
    }
}