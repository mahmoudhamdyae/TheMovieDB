package com.mahmoudhamdyae.themoviedb.grid.tvshows

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.themoviedb.MovieApiStatus
import com.mahmoudhamdyae.themoviedb.database.MovieRepository
import com.mahmoudhamdyae.themoviedb.database.getDatabase
import com.mahmoudhamdyae.themoviedb.domain.TVShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TVShowsViewModel(application: Application) : AndroidViewModel(application) {

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedTVShow = MutableLiveData<TVShow?>()
    // The external immutable LiveData for the navigation property
    val navigateToSelectedTVShow: MutableLiveData<TVShow?>
        get() = _navigateToSelectedTVShow

    // The external immutable LiveData for the request status
    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus>
        get() = _status

    private val database =
        getDatabase(application)
    private val tvShowsRepository =
        MovieRepository(database)

    val tvShowsList = tvShowsRepository.tvShows

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        getTVShows()
    }

    private fun getTVShows() {
        coroutineScope.launch {
            try {
                _status.value = MovieApiStatus.LOADING
                tvShowsRepository.refreshTVShows()
                _status.value = MovieApiStatus.DONE
            } catch (e: Exception) {
                if (tvShowsList.value.isNullOrEmpty())
                    _status.value = MovieApiStatus.ERROR
            }
        }
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedTVShow] [MutableLiveData]
     * @param tvShow The [TVShow] that was clicked on.
     */
    fun displayPropertyDetails(tvShow: TVShow) {
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