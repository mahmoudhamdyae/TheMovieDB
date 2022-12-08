package com.mahmoudhamdyae.themoviedb1.viewmodels

import androidx.lifecycle.*
import com.mahmoudhamdyae.themoviedb1.MovieApiStatus
import com.mahmoudhamdyae.themoviedb1.data.models.Review
import com.mahmoudhamdyae.themoviedb1.data.repository.MoviesRepository
import com.mahmoudhamdyae.themoviedb1.data.repository.TVShowsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class ReviewsViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    private val tvShowsRepository: TVShowsRepository,
    @Assisted
    private val movieId: String,
    @Assisted
    private val isMovie: Boolean
) : ViewModel() {

    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus>
        get() = _status

    private val _reviewsList = MutableLiveData<List<Review>>()
    val reviewsList: LiveData<List<Review>>
        get() = _reviewsList

    init {
        getReviews()
    }

    private fun getReviews() {
        viewModelScope.launch {
            try {
                _status.value = MovieApiStatus.LOADING

                if (isMovie) {
                    _reviewsList.value = moviesRepository.getReviews(movieId)
                }
                else {
                    _reviewsList.value = tvShowsRepository.getTVReviews(movieId)
                }

                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (_reviewsList.value.isNullOrEmpty())
                    _status.value = MovieApiStatus.ERROR
            }
        }
    }

    @AssistedFactory
    interface ReviewsViewModelFactory {
        fun create(movieId: String, isMovie: Boolean): ReviewsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: ReviewsViewModelFactory,
            movieId: String,
            isMovie: Boolean
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(movieId, isMovie) as T
            }
        }
    }
}