package com.mahmoudhamdyae.themoviedb1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.ui.AllFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movies = AllFragmentArgs.fromSavedStateHandle(savedStateHandle).movies.results

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList : LiveData<List<Movie>>
        get() = _moviesList

    init {
        _moviesList.value = movies
    }
}