package com.mahmoudhamdyae.themoviedb1.utility

import android.content.Context
import kotlin.math.ceil

enum class MovieApiStatus { LOADING, ERROR, DONE }

fun getNoOfColumns(context: Context?) : Int {
    val displayMetrics = context!!.resources.displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    return ceil(screenWidthDp / 185f).toInt()
}