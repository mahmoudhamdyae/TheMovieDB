package com.mahmoudhamdyae.themoviedb

import android.content.Context

enum class MovieApiStatus { LOADING, ERROR, DONE }

fun getNoOfColumns(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    return kotlin.math.ceil(screenWidthDp / 185f).toInt()
}