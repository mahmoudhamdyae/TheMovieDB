package com.mahmoudhamdyae.themoviedb1.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.themoviedb1.adapters.TrailersAdapter
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentTrailersBinding
import com.mahmoudhamdyae.themoviedb1.getNoOfColumns
import com.mahmoudhamdyae.themoviedb1.viewmodels.TrailersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrailersFragment(
    private val movie: Movie,
    private val isMovie: Boolean
): Fragment() {

    @Inject
    lateinit var trailersViewModelFactory : TrailersViewModel.TrailersViewModelFactory
    val viewModel : TrailersViewModel by viewModels { TrailersViewModel.providesFactory(
        assistedFactory = trailersViewModelFactory,
        movieId = movie.id,
        isMovie = isMovie
    ) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrailersBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.trailersList.layoutManager = GridLayoutManager(context, getNoOfColumns(context))
        binding.trailersList.adapter = TrailersAdapter(TrailersAdapter.OnClickListener{
            val url = "https://www.youtube.com/watch?v=${it.key}"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        })

        viewModel.trailersList.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.emptyTrailersTextView.visibility = View.VISIBLE
        }

        return binding.root
    }
}