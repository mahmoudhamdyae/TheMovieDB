package com.mahmoudhamdyae.themoviedb.all

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudhamdyae.themoviedb.R
import com.mahmoudhamdyae.themoviedb.databinding.FragmentAllBinding

class AllFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAllBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val movies = AllFragmentArgs.fromBundle(requireArguments()).movies.results
        val viewModelFactory = AllViewModelFactory(movies, requireActivity().application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[AllViewModel::class.java]
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