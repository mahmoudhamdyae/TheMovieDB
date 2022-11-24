package com.mahmoudhamdyae.themoviedb1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb1.MovieApiStatus
import com.mahmoudhamdyae.themoviedb1.data.models.Review
import com.mahmoudhamdyae.themoviedb1.data.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//@HiltViewModel
class ReviewsViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted
    private val movieId: String,
    @Assisted
    private val isMovie: Boolean
) : ViewModel() {

//    private val movieId  = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).selectedMovie.id
//    private val isMovie = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).selectedMovie.title != ""

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
                    _reviewsList.value = repository.getReviews(movieId)
                }
                else {
                    _reviewsList.value = repository.getTVReviews(movieId)
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

//    companion object {
//
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(
//                modelClass: Class<T>,
//                extras: CreationExtras
//            ): T {
//                // Get the Application object from extras
//                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as BaseApplication
//                // Create a SavedStateHandle for this ViewModel from extras
//                val savedStateHandle = extras.createSavedStateHandle()
//
//                return ReviewsViewModel(
//                    Repository(MovieApiService()),
//                    savedStateHandle
//                ) as T
//            }
//        }
//    }

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