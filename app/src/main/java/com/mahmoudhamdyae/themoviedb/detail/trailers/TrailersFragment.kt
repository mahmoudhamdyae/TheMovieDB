package com.mahmoudhamdyae.themoviedb.detail.trailers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mahmoudhamdyae.themoviedb.databinding.FragmentTrailersBinding

class TrailersFragment(val movieID: String):Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTrailersBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.trailerId.text = movieID

        return binding.root
    }
}