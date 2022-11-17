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

class FavouriteFragment: Fragment() {

    private lateinit var viewModel : FavouriteViewModel
    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]
        binding.viewModel = viewModel

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

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
            findNavController().navigate(FavouriteFragmentDirections.actionNavigationFavouriteToAllFragment(movies, getString(R.string.toolbar_favourite_movies)))
        }
        binding.viewAllButtonTvShows.setOnClickListener {
            val tvShows = NetworkMovieContainer(viewModel.tvShows.value)
            findNavController().navigate(FavouriteFragmentDirections.actionNavigationFavouriteToAllFragment(tvShows, getString(R.string.toolbar_favourite_tv_shows)))
        }

        viewModel.test.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.getFavourites()

        val moviesIsEmpty = viewModel.movies.value.isEmpty()
        val tvShowsIsEmpty = viewModel.tvShows.value.isEmpty()

        if (moviesIsEmpty) {
            binding.linearlayoutMovies.visibility = View.GONE
            binding.recyclerViewMovies.visibility = View.GONE
        } else {
            binding.linearlayoutMovies.visibility = View.VISIBLE
            binding.recyclerViewMovies.visibility = View.VISIBLE
        }

        if (tvShowsIsEmpty) {
            binding.linearlayoutTvShows.visibility = View.GONE
            binding.recyclerViewTvShows.visibility = View.GONE
        } else {
            binding.linearlayoutTvShows.visibility = View.VISIBLE
            binding.recyclerViewTvShows.visibility = View.VISIBLE
        }

        if (moviesIsEmpty && tvShowsIsEmpty) {
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.emptyView.visibility = View.GONE
        }

        // Hide Bottom Navigation View
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        view.visibility = View.VISIBLE
    }
}