package com.mahmoudhamdyae.themoviedb.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mahmoudhamdyae.themoviedb.all.AllAdapter
import com.mahmoudhamdyae.themoviedb.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding.viewModel = viewModel

        binding.searchRecyclerView.adapter = AllAdapter(AllAdapter.OnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(it))
        })

        return binding.root
    }
}