package com.mahmoudhamdyae.themoviedb1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb1.R
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentSummaryBinding
import com.mahmoudhamdyae.themoviedb1.viewmodels.SummaryViewModel

class SummaryFragment(private val overview: String): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSummaryBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory =
            SummaryViewModel.SummaryViewModelFactory(overview)
        val viewModel =
            ViewModelProvider(this, viewModelFactory)[SummaryViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.overview.observe(viewLifecycleOwner) {
            if (it == "") {
                binding.overview.text = getString(R.string.details_no_summary)
            } else {
                binding.overview.text = it
            }
        }

        return binding.root
    }
}