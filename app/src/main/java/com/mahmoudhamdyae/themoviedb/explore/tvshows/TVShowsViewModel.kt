package com.mahmoudhamdyae.themoviedb.explore.tvshows

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb.MovieApiStatus
import com.mahmoudhamdyae.themoviedb.database.network.Movie
import com.mahmoudhamdyae.themoviedb.database.network.MovieApi
import com.mahmoudhamdyae.themoviedb.database.network.NetworkMovieContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TVShowsViewModel(page: Int, application: Application) : AndroidViewModel(application) {

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

    private val _listOfPopularContainer = MutableLiveData<NetworkMovieContainer>()
    val listOfPopularContainer: LiveData<NetworkMovieContainer>
        get() = _listOfPopularContainer

    private val _listOfTopRatedContainer = MutableLiveData<NetworkMovieContainer>()
    val listOfTopRatedContainer: LiveData<NetworkMovieContainer>
        get() = _listOfTopRatedContainer

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getTVShowsPopular(page)
        getTVShowsTopRated(page)
    }

    private fun getTVShowsPopular(page: Int) {
        coroutineScope.launch {
            try {
                _statusPopular.value = MovieApiStatus.LOADING

                _listOfPopularContainer.value = MovieApi.retrofitService.getPopularTVShowsAsync(page.toString()).await()
                _tvShowsListPopular.value = MovieApi.retrofitService.getPopularTVShowsAsync(page.toString()).await().results

                _statusPopular.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (tvShowsListPopular.value.isNullOrEmpty())
                    _statusPopular.value = MovieApiStatus.ERROR
            }
        }
    }

    private fun getTVShowsTopRated(page: Int) {
        coroutineScope.launch {
            try {
                _statusTopRated.value = MovieApiStatus.LOADING

                _listOfTopRatedContainer.value = MovieApi.retrofitService.getTopRatedTVShowsAsync().await()
                _tvShowsListTopRated.value = MovieApi.retrofitService.getTopRatedTVShowsAsync().await().results

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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}