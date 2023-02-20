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
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseUser
import com.mahmoudhamdyae.themoviedb1.MainActivity
import com.mahmoudhamdyae.themoviedb1.R
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentDetailBinding
import com.mahmoudhamdyae.themoviedb1.utility.launchSignInFlow
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

        viewModel.isFavourite.observe(viewLifecycleOwner) {
            show(it)
        }

        var user: FirebaseUser?  = null
        viewModel.user.observe(viewLifecycleOwner) {
            user = it
        }
        binding.favouriteButton.setOnClickListener {
            if (user != null) {
                // User signed in
                viewModel.isFavourite.observe(viewLifecycleOwner) { isFavourite ->
                    animate(isFavourite!!)
                    if (isFavourite) {
                        viewModel.delMovie(movie)
                    } else {
                        viewModel.insertMovie(movie)
                    }
                    viewModel.setIsFavourite(!isFavourite)
                }
            } else {
                // No user signed
                Snackbar.make(requireView(), getString(R.string.log_in_label), Snackbar.LENGTH_SHORT)
                    .setAction(R.string.log_in_button) {
                        launchSignInFlow(signInLauncher)
                    }.show()
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

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()) {}
}