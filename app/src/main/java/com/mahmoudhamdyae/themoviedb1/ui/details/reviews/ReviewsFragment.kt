package com.mahmoudhamdyae.themoviedb1.ui.details.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentReviewsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReviewsFragment(
    private val movie: Movie,
    private val isMovie: Boolean
): Fragment() {

    @Inject
    lateinit var reviewsViewModelFactory : ReviewsViewModel.ReviewsViewModelFactory
    private val viewModel : ReviewsViewModel by viewModels { ReviewsViewModel.providesFactory(
        assistedFactory = reviewsViewModelFactory,
        movieId = movie.id,
        isMovie = isMovie
    ) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReviewsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.reviewsList.adapter = ReviewsAdapter()

        viewModel.reviewsList.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.emptyReviewsTextView.visibility = View.VISIBLE
        }

        return binding.root
    }
}