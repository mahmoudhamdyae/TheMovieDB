package com.mahmoudhamdyae.themoviedb.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoudhamdyae.themoviedb.network.MovieApi
import com.mahmoudhamdyae.themoviedb.network.NetworkMovieContainer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val response: LiveData<String>
        get() = _response

    init {
        getMovies()
    }

    private fun getMovies() {
        MovieApi.retrofitService.getPopularMovies().enqueue( object: Callback<NetworkMovieContainer> {
            override fun onFailure(call: Call<NetworkMovieContainer>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<NetworkMovieContainer>, response: Response<NetworkMovieContainer>) {
                _response.value = "Success: ${response.body()?.results?.size} Movies  retrieved"
            }
        })
    }
}