package com.mahmoudhamdyae.themoviedb1.ui.details

import androidx.lifecycle.*
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.room.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
        var ret = false
        runBlocking {
//            ret = repository.getFavouriteMovies().first().contains(movie)
            repository.getMoviesFromFirebase().addOnSuccessListener { result ->
                for (document in result) {
                    if (movie.id == document.id) {
                        ret = true
                        break
                    }
                }
            }.addOnFailureListener {
                _error.value = it.message.toString()
            }
        }
        return ret
    }

    fun delMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
//                    repository.deleteFavouriteMovie(movie)
                    repository.delMovieFromFirebase(movie.id).addOnFailureListener {
                        _error.value = it.message.toString()
                    }
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
//                    repository.insertFavouriteMovie(movie)
                    repository.addMovieToFirebase(movie).addOnFailureListener {
                        _error.value = it.message.toString()
                    }
                }
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }
}