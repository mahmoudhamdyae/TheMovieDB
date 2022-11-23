package com.mahmoudhamdyae.themoviedb1.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkTrailerContainer(val results: List<Trailer>)

@JsonClass(generateAdapter = true)
data class Trailer(
    val id: String,
    val name: String,
    val key: String,
    val site: String
)