package com.mahmoudhamdyae.themoviedb.explore.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudhamdyae.themoviedb.R
import com.mahmoudhamdyae.themoviedb.database.network.Movie
import com.mahmoudhamdyae.themoviedb.databinding.FragmentTvShowsBinding
import com.mahmoudhamdyae.themoviedb.explore.MovieExploreAdapter
import com.mahmoudhamdyae.themoviedb.explore.ExploreFragmentDirections

class TVShowsFragment : Fragment() {

    private var _binding: FragmentTvShowsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowsBinding.inflate(inflater, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Initialize [MoviesViewModel].
        val viewModelFactory = TVShowsViewModelFactory(1, requireActivity().application)
        val viewModel : TVShowsViewModel = ViewModelProvider(this, viewModelFactory)[TVShowsViewModel::class.java]

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.photosGridPopular.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.photosGridPopular.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        binding.photosGridTopRated.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.photosGridTopRated.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedTVShow.observe(viewLifecycleOwner, Observer(fun(tvShow : Movie?) {
            if (null != tvShow) {
                findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToDetailFragment(tvShow))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        }))

        binding.viewAllButtonPopular.setOnClickListener {
            viewModel.listOfPopularContainer.observe(viewLifecycleOwner) {
                findNavController().navigate(ExploreFragmentDirections.actionNavigationExploreToAllFragment(it, getString(R.string.toolbar_popular_tv_shows)))
            }
        }

        binding.viewAllButtonTopRated.setOnClickListener {
            viewModel.listOfTopRatedContainer.observe(viewLifecycleOwner) {
                findNavController().navigate(ExploreFragmentDirections.actionNavigationExploreToAllFragment(it, getString(R.string.toolbar_top_rated_tv_shows)))
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}