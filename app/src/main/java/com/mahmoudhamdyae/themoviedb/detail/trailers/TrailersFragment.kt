package com.mahmoudhamdyae.themoviedb.detail.trailers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb.databinding.FragmentTrailersBinding


class TrailersFragment(private val movieID: String, val isMovie: Boolean):Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrailersBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = TrailersViewModelFactory(movieID, isMovie, requireActivity().application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TrailersViewModel::class.java]
        binding.viewModel = viewModel

        binding.trailersList.adapter = TrailersAdapter(TrailersAdapter.OnClickListener{
            val url = "https://www.youtube.com/watch?v=${it.key}"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        })

        return binding.root
    }
}