package com.mahmoudhamdyae.themoviedb1.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.repository.Repository
import com.mahmoudhamdyae.themoviedb1.utility.MovieApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVShowsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedTVShow = MutableLiveData<Movie?>()
    // The external immutable LiveData for the navigation property
    val navigateToSelectedTVShow: MutableLiveData<Movie?>
        get() = _navigateToSelectedTVShow

    // The external immutable LiveData for the request status
    private val _statusPopular = MutableLiveData<MovieApiStatus>()
    val statusPopular: LiveData<MovieApiStatus>
        get() = _statusPopular

    private val _statusTopRated = MutableLiveData<MovieApiStatus>()
    val statusTopRated: LiveData<MovieApiStatus>
        get() = _statusTopRated

    private val _tvShowsListPopular = MutableLiveData<List<Movie>>()
    val tvShowsListPopular: LiveData<List<Movie>>
        get() = _tvShowsListPopular

    private val _tvShowsListTopRated = MutableLiveData<List<Movie>>()
    val tvShowsListTopRated: LiveData<List<Movie>>
        get() = _tvShowsListTopRated

    init {
        getTVShowsPopular()
        getTVShowsTopRated()
    }

    fun getTVShowsPopular() {
        viewModelScope.launch {
            try {
                _statusPopular.value = MovieApiStatus.LOADING
                _tvShowsListPopular.value = repository.getPopularTVShows(1)
                _statusPopular.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (tvShowsListPopular.value.isNullOrEmpty())
                    _statusPopular.value = MovieApiStatus.ERROR
            }
        }
    }

    fun getTVShowsTopRated() {
        viewModelScope.launch {
            try {
                _statusTopRated.value = MovieApiStatus.LOADING
                _tvShowsListTopRated.value = repository.getTopRatedTVShows(1)
                _statusTopRated.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (tvShowsListPopular.value.isNullOrEmpty())
                    _statusTopRated.value = MovieApiStatus.ERROR
            }
        }
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedTVShow] [MutableLiveData]
     * @param tvShow The [Movie] that was clicked on.
     */
    fun displayPropertyDetails(tvShow: Movie) {
        _navigateToSelectedTVShow.value = tvShow
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedTVShow.value = null
    }
}