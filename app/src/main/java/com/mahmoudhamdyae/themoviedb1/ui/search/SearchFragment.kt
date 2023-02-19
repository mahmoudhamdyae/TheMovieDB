package com.mahmoudhamdyae.themoviedb1.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mahmoudhamdyae.themoviedb1.R
import com.mahmoudhamdyae.themoviedb1.data.models.NetworkMovieContainer
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentSearchBinding
import com.mahmoudhamdyae.themoviedb1.ui.explore.MovieExploreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel : SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this

        // View Model
        binding.viewModel = viewModel

        // Toolbar
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Movies recycler view
        binding.searchRecyclerViewMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.searchRecyclerViewMovies.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(it))
        })

        // TV shows recycler view
        binding.searchRecyclerViewTvShows.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.searchRecyclerViewTvShows.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(it))
        })

        // Search button
        binding.searchButton.setOnClickListener {
            getMoviesAndTVShows()
        }

        // Search edit text
        binding.searchEditText.setOnEditorActionListener(
            OnEditorActionListener { _, actionId, _ ->
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    getMoviesAndTVShows()
                    return@OnEditorActionListener true
                }
                // Return true if you have consumed the action, else false.
                false
            })

        // View all button (movies)
        binding.viewAllButtonMovies.setOnClickListener {
            viewModel.movies.observe(viewLifecycleOwner) {
                findNavController().navigate(SearchFragmentDirections.actionNavigationSearchToAllFragment(
                    NetworkMovieContainer(it), getString(R.string.toolbar_search_movies)))
            }
        }

        // View all button (tv shows)
        binding.viewAllButtonTvShows.setOnClickListener {
            viewModel.tvShows.observe(viewLifecycleOwner) {
                findNavController().navigate(SearchFragmentDirections.actionNavigationSearchToAllFragment(
                    NetworkMovieContainer(it), getString(R.string.toolbar_search_tv_shows)))
            }
        }

        // Visibility of movies layout
        viewModel.movies.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.linearLayout.visibility = View.GONE
            else
                binding.linearLayout.visibility = View.VISIBLE
        }

        // Visibility of tv shows layout
        viewModel.tvShows.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.linearLayout.visibility = View.GONE
            else
                binding.linearLayout.visibility = View.VISIBLE
        }

        // Toast Error
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun getMoviesAndTVShows() {
        val editText = binding.searchEditText.text.toString()
        if (editText.isNotBlank()) {
            binding.linearLayout.visibility = View.VISIBLE
            hideKeyboard()
            viewModel.getSearchedMovies(editText)
            viewModel.getSearchedTVShows(editText)
        }
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