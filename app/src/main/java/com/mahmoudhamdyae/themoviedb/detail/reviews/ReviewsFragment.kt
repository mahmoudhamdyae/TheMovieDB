package com.mahmoudhamdyae.themoviedb.detail.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb.databinding.FragmentReviewsBinding

class ReviewsFragment(val movieID: String): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReviewsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = ReviewsViewModelFactory(movieID, requireActivity().application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[ReviewsViewModel::class.java]
        binding.viewModel = viewModel

        binding.reviewsList.adapter = ReviewsAdapter()

        viewModel.reviewIsEmpty.observe(viewLifecycleOwner) {
            if (it)
                binding.emptyReviewsTextView.visibility = View.VISIBLE
        }
        binding.emptyReviewsTextView.visibility

        return binding.root
    }
}