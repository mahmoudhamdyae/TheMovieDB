package com.mahmoudhamdyae.themoviedb1.ui.details

import androidx.lifecycle.*
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movie = DetailFragmentArgs.fromSavedStateHandle(savedStateHandle).selectedMovie

    private val _selectedProperty = MutableLiveData<Movie>()
    val selectedProperty: LiveData<Movie>
        get() = _selectedProperty

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _isFavourite = MutableLiveData(false)
    val isFavourite: LiveData<Boolean>
        get() = _isFavourite

    init {
        _selectedProperty.value = movie
        if (repository.getUser() != null) {
            getFavouriteFromFirebase(movie)
        }
    }

    fun getCurrentUser() = repository.getUser()

    private fun getFavouriteFromFirebase(movie: Movie) {
        viewModelScope.launch {
            repository.getMoviesFromFirebase().addOnSuccessListener { result ->
                for (document in result) {
                    if (movie.id == document.id) {
                        _isFavourite.value = true
                        break
                    }
                }
            }.addOnFailureListener {
                _error.value = it.message.toString()
            }
        }
    }

    fun delMovie(movie: Movie) {
        try {
            viewModelScope.launch {
                repository.delMovieFromFirebase(movie.id).addOnFailureListener {
                    _error.value = it.message.toString()
                }
            }
            _isFavourite.value = false
        } catch (e: Exception) {
            _error.value = e.toString()
        }
    }

    fun insertMovie(movie: Movie) {
        try {
            viewModelScope.launch {
                repository.addMovieToFirebase(movie).addOnFailureListener {
                    _error.value = it.message.toString()
                }
            }
            _isFavourite.value = true
        } catch (e: Exception) {
            _error.value = e.toString()
        }
    }
}