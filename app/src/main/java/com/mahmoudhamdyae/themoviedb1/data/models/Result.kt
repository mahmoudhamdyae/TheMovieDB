package com.mahmoudhamdyae.themoviedb1.data.models

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

inline fun <reified T> Result<T>.doIfFailure(callback: (exception: String?) -> Unit) {
    if (this is Result.Error) {
        callback(exception)
    }
}

inline fun <reified T> Result<T>.doIfSuccess(callback: (data: T) -> Unit) {
    if (this is Result.Success) {
        callback(data)
    }
}