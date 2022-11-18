package com.mahmoudhamdyae.themoviedb.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mahmoudhamdyae.themoviedb.R
import com.mahmoudhamdyae.themoviedb.databinding.FragmentSearchBinding
import com.mahmoudhamdyae.themoviedb.explore.MovieExploreAdapter

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

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.searchRecyclerViewMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.searchRecyclerViewMovies.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(it))
        })

        binding.searchRecyclerViewTvShows.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.searchRecyclerViewTvShows.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(it))
        })

        binding.linearLayout.visibility = View.GONE

        binding.searchButton.setOnClickListener {
            if (binding.searchEditText.text.toString().isNotBlank()) {
                binding.linearLayout.visibility = View.VISIBLE
                hideKeyboard()
                viewModel.getSearchedMovies(binding.searchEditText.text.toString())
                viewModel.getSearchedTVShows(binding.searchEditText.text.toString())
            }
        }

        binding.searchEditText.setOnEditorActionListener(
            OnEditorActionListener { _, actionId, _ ->
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (binding.searchEditText.text.toString().isNotBlank()) {
                        binding.linearLayout.visibility = View.VISIBLE
                        hideKeyboard()
                        viewModel.getSearchedMovies(binding.searchEditText.text.toString())
                        viewModel.getSearchedTVShows(binding.searchEditText.text.toString())
                    }
                    return@OnEditorActionListener true
                }
                // Return true if you have consumed the action, else false.
                false
            })

        binding.viewAllButtonMovies.setOnClickListener {
            viewModel.moviesContainer.observe(viewLifecycleOwner) {
                findNavController().navigate(SearchFragmentDirections.actionNavigationSearchToAllFragment(it, getString(R.string.toolbar_search_movies)))
            }
        }

        binding.viewAllButtonTvShows.setOnClickListener {
            viewModel.tvShowsContainer.observe(viewLifecycleOwner) {
                findNavController().navigate(SearchFragmentDirections.actionNavigationSearchToAllFragment(it, getString(R.string.toolbar_search_tv_shows)))
            }
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.linearLayout.visibility = View.GONE
            else
                binding.linearLayout.visibility = View.VISIBLE
        }

        viewModel.tvShows.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.linearLayout.visibility = View.GONE
            else
                binding.linearLayout.visibility = View.VISIBLE
        }

        return binding.root
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        view.visibility = View.VISIBLE
    }
}