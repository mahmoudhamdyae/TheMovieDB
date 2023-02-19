package com.mahmoudhamdyae.themoviedb1.ui.details

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseUser
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

    private val _isFavourite = MutableLiveData(false)
    val isFavourite: LiveData<Boolean>
        get() = _isFavourite

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?>
        get() = _user

    init {
        getCurrentUser()

        _selectedProperty.value = movie
        if (_user.value != null) {
            setIsFavourite(movie)
        }
    }

    private fun getCurrentUser() {
        _user.value = repository.getUser()
    }

    private fun setIsFavourite(movie: Movie) {
        runBlocking {
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

    fun setF(f: Boolean) {
        _isFavourite.value = f
    }

    fun delMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
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