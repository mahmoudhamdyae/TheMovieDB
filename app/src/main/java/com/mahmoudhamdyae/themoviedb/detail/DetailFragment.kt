package com.mahmoudhamdyae.themoviedb.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.mahmoudhamdyae.themoviedb.MainActivity
import com.mahmoudhamdyae.themoviedb.R
import com.mahmoudhamdyae.themoviedb.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        (activity as MainActivity).makeBottomNavigationViewInvisible()

        val movie = DetailFragmentArgs.fromBundle(requireArguments()).selectedMovie
        val viewModelFactory = DetailViewModelFactory(movie, requireActivity().application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        binding.viewModel = viewModel

        // Navigate Up
        val toolbar = binding.toolbar
        toolbar.title = movie.realName
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.favouriteButton.setOnClickListener {
            if (viewModel.isFavourite(movie)) {
                viewModel.delMovie(movie)
            } else {
                viewModel.insertMovie(movie)
            }
        }

        viewModel.test.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        // Tabs

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        val adapter = DetailsViewPagerAdapter(this, movie, movie.title != "")
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.summary_fragment)
                1 -> getString(R.string.reviews_fragment)
                else -> getString(R.string.trailers_fragment)
            }
        }.attach()

        return binding.root
    }
}