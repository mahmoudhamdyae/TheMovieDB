package com.mahmoudhamdyae.themoviedb.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mahmoudhamdyae.themoviedb.R
import com.mahmoudhamdyae.themoviedb.database.network.NetworkMovieContainer
import com.mahmoudhamdyae.themoviedb.databinding.FragmentFavouriteBinding
import com.mahmoudhamdyae.themoviedb.explore.MovieExploreAdapter
import kotlinx.coroutines.flow.observeOn

class FavouriteFragment: Fragment() {

    private lateinit var viewModel : FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavouriteBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]
        binding.viewModel = viewModel

        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMovies.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToDetailFragment(it))
        })

        binding.recyclerViewTvShows.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewTvShows.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToDetailFragment(it))
        })

        binding.viewAllButtonMovies.setOnClickListener {
            val movies = NetworkMovieContainer(viewModel.movies.value)
            findNavController().navigate(FavouriteFragmentDirections.actionNavigationFavouriteToAllFragment(movies))
        }
        binding.viewAllButtonTvShows.setOnClickListener {
            val tvShows = NetworkMovieContainer(viewModel.tvShows.value)
            findNavController().navigate(FavouriteFragmentDirections.actionNavigationFavouriteToAllFragment(tvShows))
        }

        viewModel.test.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.getFavourites()

        // Hide Bottom Navigation View
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        view.visibility = View.VISIBLE
    }
}