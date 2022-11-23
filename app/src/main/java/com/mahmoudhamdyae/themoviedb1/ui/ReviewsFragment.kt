package com.mahmoudhamdyae.themoviedb1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb1.adapters.ReviewsAdapter
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentReviewsBinding
import com.mahmoudhamdyae.themoviedb1.viewmodels.ReviewsViewModel
import com.mahmoudhamdyae.themoviedb1.viewmodelsfactory.ReviewsViewModelFactory

class ReviewsFragment(private val movieID: String, private val isMovie: Boolean): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReviewsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = ReviewsViewModelFactory(movieID, isMovie, requireActivity().application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[ReviewsViewModel::class.java]
        binding.viewModel = viewModel

        binding.reviewsList.adapter = ReviewsAdapter()

        viewModel.reviewsList.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.emptyReviewsTextView.visibility = View.VISIBLE
        }

        return binding.root
    }
}