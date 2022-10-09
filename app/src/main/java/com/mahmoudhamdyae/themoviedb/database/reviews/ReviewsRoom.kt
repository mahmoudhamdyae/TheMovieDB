package com.mahmoudhamdyae.themoviedb.database.reviews

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmoudhamdyae.themoviedb.domain.Review

@Entity
class ReviewsRoom (
    val id: String,
    @PrimaryKey
    val author: String,
    val content: String
)

fun List<ReviewsRoom>.asDomainModel() : List<Review> {
    return map {
        Review(
            id = it .id,
            author = it.author,
            content = it.content
        )
    }
}