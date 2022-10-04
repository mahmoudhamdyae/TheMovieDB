package com.mahmoudhamdyae.themoviedb.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment () {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMoviesBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Initialize [MoviesViewModel].
        val viewModel : MoviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        return binding.root
    }
}