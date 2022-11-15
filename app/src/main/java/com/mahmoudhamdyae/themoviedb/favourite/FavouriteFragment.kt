package com.mahmoudhamdyae.themoviedb.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb.databinding.FragmentFavouriteBinding

class FavouriteFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavouriteBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]

        return binding.root
    }
}