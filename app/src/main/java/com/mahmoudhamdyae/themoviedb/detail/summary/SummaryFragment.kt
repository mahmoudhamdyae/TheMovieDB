package com.mahmoudhamdyae.themoviedb.detail.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb.databinding.FragmentSummaryBinding

class SummaryFragment(val overview: String): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSummaryBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = SummaryViewModelFactory(overview, requireActivity().application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SummaryViewModel::class.java]
        binding.viewModel = viewModel

        return binding.root
    }
}