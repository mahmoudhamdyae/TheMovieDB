package com.mahmoudhamdyae.themoviedb.detail.reviews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb.MovieApiStatus
import com.mahmoudhamdyae.themoviedb.database.network.MovieApi
import com.mahmoudhamdyae.themoviedb.database.network.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ReviewsViewModel(
    private val movieID: String,
    private val isMovie: Boolean,
    application: Application
) : AndroidViewModel(application) {

    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus>
        get() = _status

    private val _reviewsList = MutableLiveData<List<Review>>()
    val reviewsList: LiveData<List<Review>>
        get() = _reviewsList

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getReviews()
    }

    private fun getReviews() {
        coroutineScope.launch {
            try {
                _status.value = MovieApiStatus.LOADING

                if (isMovie) {
                    _reviewsList.value = MovieApi.retrofitService.getReviewsAsync(movieID).await().results
                }
                else {
                    _reviewsList.value = MovieApi.retrofitService.getTVReviewsAsync(movieID).await().results
                }

                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (_reviewsList.value.isNullOrEmpty())
                    _status.value = MovieApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}