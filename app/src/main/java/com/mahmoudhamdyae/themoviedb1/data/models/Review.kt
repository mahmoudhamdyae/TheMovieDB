package com.mahmoudhamdyae.themoviedb1.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkReviewContainer(val results: List<Review>)

@JsonClass(generateAdapter = true)
data class Review(
    val id: String,
    val author: String,
    val content: String
)