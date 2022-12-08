package com.mahmoudhamdyae.themoviedb1.viewmodels

import androidx.lifecycle.*
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.room.FavouriteRepository
import com.mahmoudhamdyae.themoviedb1.ui.DetailFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: FavouriteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movie = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).selectedMovie

    private val _selectedProperty = MutableLiveData<Movie>()
    val selectedProperty: LiveData<Movie>
        get() = _selectedProperty

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        _selectedProperty.value = movie
    }

    fun isFavourite(movie: Movie): Boolean {
        var ret: Boolean
        runBlocking {
            ret = repository.getFavouriteMovies().first().contains(movie)
        }
        return ret
    }

    fun delMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.deleteFavouriteMovie(movie)
                }
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }

    fun insertMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.insertFavouriteMovie(movie)
                }
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }
}