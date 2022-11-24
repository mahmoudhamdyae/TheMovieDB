package com.mahmoudhamdyae.themoviedb1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.themoviedb1.adapters.AllAdapter
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentAllBinding
import com.mahmoudhamdyae.themoviedb1.viewmodels.AllViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAllBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModel : AllViewModel by viewModels()
        binding.viewModel = viewModel

        binding.toolbar.title = AllFragmentArgs.fromBundle(requireArguments()).toolbarText
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.photosGrid.layoutManager = GridLayoutManager(context, 1)
        binding.photosGrid.adapter = AllAdapter(AllAdapter.OnClickListener {
            findNavController().navigate(AllFragmentDirections.actionAllFragmentToDetailFragment(it))
        })

        return binding.root
    }
}