package com.mahmoudhamdyae.themoviedb.detail.reviews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb.MovieApiStatus
import com.mahmoudhamdyae.themoviedb.database.MovieRepository
import com.mahmoudhamdyae.themoviedb.database.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ReviewsViewModel(private val movieID: String, application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus>
        get() = _status

    private val database =
        getDatabase(application)
    private val reviewsRepository =
        MovieRepository(database)

    val reviewsList = reviewsRepository.reviews

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getReviews()
    }

    private fun getReviews() {
        coroutineScope.launch {
            try {
                _status.value = MovieApiStatus.LOADING
                reviewsRepository.refreshReviews(movieID)
                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (reviewsList.value.isNullOrEmpty())
                    _status.value = MovieApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}