package com.mahmoudhamdyae.themoviedb1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mahmoudhamdyae.themoviedb1.R
import com.mahmoudhamdyae.themoviedb1.adapters.MovieExploreAdapter
import com.mahmoudhamdyae.themoviedb1.data.models.NetworkMovieContainer
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentFavouriteBinding
import com.mahmoudhamdyae.themoviedb1.viewmodels.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment: Fragment() {

    private val viewModel : FavouriteViewModel by viewModels()
    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Toolbar
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Movies RecyclerView
        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMovies.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToDetailFragment(it))
        })

        // TV Shows RecyclerView
        binding.recyclerViewTvShows.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewTvShows.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToDetailFragment(it))
        })

        binding.viewAllButtonMovies.setOnClickListener {
            val movies = NetworkMovieContainer(viewModel.movies.value)
            findNavController().navigate(FavouriteFragmentDirections.actionNavigationFavouriteToAllFragment(movies, getString(R.string.toolbar_favourite_movies)))
        }
        binding.viewAllButtonTvShows.setOnClickListener {
            val tvShows = NetworkMovieContainer(viewModel.tvShows.value)
            findNavController().navigate(FavouriteFragmentDirections.actionNavigationFavouriteToAllFragment(tvShows, getString(R.string.toolbar_favourite_tv_shows)))
        }

        // Toast Error Message
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Show Bottom Navigation View
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        view.visibility = View.VISIBLE
    }
}