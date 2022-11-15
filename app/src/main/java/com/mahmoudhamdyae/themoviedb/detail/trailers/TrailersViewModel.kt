package com.mahmoudhamdyae.themoviedb.detail.trailers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb.MovieApiStatus
import com.mahmoudhamdyae.themoviedb.database.network.MovieApi
import com.mahmoudhamdyae.themoviedb.database.network.Trailer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TrailersViewModel(
    private val movieID: String,
    private val isMovie: Boolean,
    application: Application
) : AndroidViewModel(application) {

    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus>
        get() = _status

    private val _trailersList = MutableLiveData<List<Trailer>>()
    val trailersList: LiveData<List<Trailer>>
        get() = _trailersList

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getTrailers()
    }

    private fun getTrailers() {
        coroutineScope.launch {
            try {
                _status.value = MovieApiStatus.LOADING

                if (isMovie) {
                    _trailersList.value = MovieApi.retrofitService.getTrailersAsync(movieID).await().results
                }
                else {
                    _trailersList.value = MovieApi.retrofitService.getTVTrailersAsync(movieID).await().results
                }

                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (_trailersList.value.isNullOrEmpty())
                    _status.value = MovieApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}