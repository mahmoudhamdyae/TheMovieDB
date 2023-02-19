package com.mahmoudhamdyae.themoviedb1.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.room.FavouriteRepository
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

    init {
        visibilityOfMovies()
        visibilityOfTVShows()

        getFavourites()

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

    private fun getFavourites() {
        viewModelScope.launch {
            try {
                repository.getFavouriteMovies().collect {
                    _movies.value = listOf()
                    _tvShows.value = listOf()
                    it.forEach {movie : Movie ->
                        if (movie.title != "") {
                            // Movie
                            _movies.value = _movies.value + movie
                        } else {
                            // TV Show
                            _tvShows.value = _tvShows.value + movie
                        }
                    }
                    _movies.value = _movies.value.reversed()
                    _tvShows.value = _tvShows.value.reversed()
                }
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }
}