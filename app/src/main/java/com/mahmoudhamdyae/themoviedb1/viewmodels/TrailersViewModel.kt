package com.mahmoudhamdyae.themoviedb1.viewmodels

import androidx.lifecycle.*
import com.mahmoudhamdyae.themoviedb1.MovieApiStatus
import com.mahmoudhamdyae.themoviedb1.data.models.Trailer
import com.mahmoudhamdyae.themoviedb1.data.repository.MoviesRepository
import com.mahmoudhamdyae.themoviedb1.data.repository.TVShowsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class TrailersViewModel @AssistedInject constructor(
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

    private val _trailersList = MutableLiveData<List<Trailer>>()
    val trailersList: LiveData<List<Trailer>>
        get() = _trailersList

    init {
        getTrailers()
    }

    private fun getTrailers() {
        viewModelScope.launch {
            try {
                _status.value = MovieApiStatus.LOADING

                if (isMovie) {
                    _trailersList.value = moviesRepository.getTrailers(movieId)
                }
                else {
                    _trailersList.value = tvShowsRepository.getTVTrailers(movieId)
                }

                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (_trailersList.value.isNullOrEmpty())
                    _status.value = MovieApiStatus.ERROR
            }
        }
    }

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