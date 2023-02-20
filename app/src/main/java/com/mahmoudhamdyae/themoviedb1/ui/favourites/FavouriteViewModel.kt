package com.mahmoudhamdyae.themoviedb1.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.room.FavouriteRepository
import com.mahmoudhamdyae.themoviedb1.utility.MovieApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: FavouriteRepository
): ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(listOf())
    val movies: MutableStateFlow<List<Movie>>
        get() = _movies

    private val _tvShows = MutableStateFlow<List<Movie>>(listOf())
    val tvShows: MutableStateFlow<List<Movie>>
        get() = _tvShows

    private val _isEmptyMovies = MutableLiveData<Boolean>()
    val isEmptyMovies : LiveData<Boolean>
        get() = _isEmptyMovies

    private val _isEmptyTVShows = MutableLiveData<Boolean>()
    val isEmptyTVShows : LiveData<Boolean>
        get() = _isEmptyTVShows

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?>
        get() = _user

    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus>
        get() = _status

    init {
        visibilityOfMovies()
        visibilityOfTVShows()

        getCurrentUser()
    }

    fun getCurrentUser() {
        _user.value = repository.getUser()
    }

    private fun visibilityOfMovies() {
        viewModelScope.launch {
            _movies.collect {
                _isEmptyMovies.value = it.isEmpty()
            }
        }
    }

    private fun visibilityOfTVShows() {
        viewModelScope.launch {
            _tvShows.collect {
                _isEmptyTVShows.value = it.isEmpty()
            }
        }
    }

    fun getFavourites() {
        viewModelScope.launch {
            _status.value = MovieApiStatus.LOADING
            if (_user.value != null) {
                repository.getMoviesFromFirebase().addOnSuccessListener { result ->
                    val moviesList : MutableList<Movie> = mutableListOf()
                    val tvShowsList : MutableList<Movie> = mutableListOf()
                    for (document in result) {
                        val movieItem = Movie(
                            id = document.id,
                            title = document.get("title").toString(),
                            name = document.get("name").toString(),
                            posterPath = document.get("posterPath").toString(),
                            overview = document.get("overview").toString(),
                            userRating = document.get("userRating").toString(),
                            releaseDate = document.get("releaseDate").toString()
                        )
                        if (movieItem.title != "") {
                            // Movie
                            moviesList.add(movieItem)
                        } else {
                            // TV Show
                            tvShowsList.add(movieItem)
                        }
                    }
                    _movies.value = moviesList
                    _tvShows.value = tvShowsList
                    _status.value = MovieApiStatus.DONE
                }.addOnFailureListener {
                    _error.value = it.message.toString()
                    _status.value = MovieApiStatus.ERROR
                }
            }
        }
    }
}