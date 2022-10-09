package com.mahmoudhamdyae.themoviedb.detail.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mahmoudhamdyae.themoviedb.databinding.FragmentReviewsBinding

class ReviewsFragment(val movieID: String): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReviewsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.reviewId.text = movieID
        return binding.root
    }
}