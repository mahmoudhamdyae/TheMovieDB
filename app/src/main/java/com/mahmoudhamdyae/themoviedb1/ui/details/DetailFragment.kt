package com.mahmoudhamdyae.themoviedb1.ui.details

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.mahmoudhamdyae.themoviedb1.MainActivity
import com.mahmoudhamdyae.themoviedb1.R
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var emptyHeart: AnimatedVectorDrawable
    private lateinit var fillHeart: AnimatedVectorDrawable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        (activity as MainActivity).makeBottomNavigationViewInvisible()

        val movie = DetailFragmentArgs.fromBundle(requireArguments()).selectedMovie
        val viewModel : DetailViewModel by viewModels()
        binding.viewModel = viewModel

        // Navigate Up
        val toolbar = binding.toolbar
        toolbar.title = movie.realName
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        emptyHeart = ResourcesCompat.getDrawable(resources, R.drawable.avd_heart_empty, null) as AnimatedVectorDrawable
        fillHeart = ResourcesCompat.getDrawable(resources, R.drawable.avd_heart_fill, null) as AnimatedVectorDrawable

        show(viewModel.isFavourite(movie))
        binding.favouriteButton.setOnClickListener {
            animate(viewModel.isFavourite(movie))
            if (viewModel.isFavourite(movie)) {
                viewModel.delMovie(movie)
            } else {
                viewModel.insertMovie(movie)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
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

    private fun show(full: Boolean) {
        val drawable = if (full) R.drawable.ic_heart_full else R.drawable.ic_heart
        binding.favouriteButton.setImageResource(drawable)
    }

    private fun animate(full: Boolean) {
        val drawable = if (full) emptyHeart else fillHeart
        binding.favouriteButton.setImageDrawable(drawable)
        drawable.start()
        show(!full)
    }
}