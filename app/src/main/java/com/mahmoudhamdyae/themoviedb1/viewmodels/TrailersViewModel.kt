package com.mahmoudhamdyae.themoviedb1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb1.MovieApiStatus
import com.mahmoudhamdyae.themoviedb1.data.models.Trailer
import com.mahmoudhamdyae.themoviedb1.data.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//@HiltViewModel
class TrailersViewModel @AssistedInject constructor(
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
                    _trailersList.value = repository.getTrailers(movieId)
                }
                else {
                    _trailersList.value = repository.getTVTrailers(movieId)
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
//                return TrailersViewModel(
//                    Repository(MovieApiService(ApiService)),
//                    savedStateHandle
//                ) as T
//            }
//        }
//    }

    @AssistedFactory
    interface TrailersViewModelFactory {
        fun create(movieId: String, isMovie: Boolean): TrailersViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: TrailersViewModelFactory,
            movieId: String,
            isMovie: Boolean
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(movieId, isMovie) as T
            }
        }
    }
}