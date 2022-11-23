package com.mahmoudhamdyae.themoviedb1.viewmodelsfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb1.viewmodels.SummaryViewModel

/**
 * Simple ViewModel factory that provides the Summary and context to the ViewModel.
 */
class SummaryViewModelFactory(
    private val overview: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SummaryViewModel::class.java)) {
            return SummaryViewModel(overview, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}