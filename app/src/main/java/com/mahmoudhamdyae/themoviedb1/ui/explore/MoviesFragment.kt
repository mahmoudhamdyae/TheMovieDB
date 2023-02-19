package com.mahmoudhamdyae.themoviedb1.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudhamdyae.themoviedb1.R
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.models.NetworkMovieContainer
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment () {

    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Initialize [MoviesViewModel].
        val viewModel : MoviesViewModel by activityViewModels()

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getMoviesPopular()
            viewModel.getMoviesTopRated()
        }

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
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer(fun(movie : Movie?) {
            if (null != movie) {
                findNavController().navigate(ExploreFragmentDirections.actionExploreFragmentToDetailFragment(movie))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        }))

        binding.viewAllButtonPopular.setOnClickListener {
            viewModel.moviesListPopular.observe(viewLifecycleOwner) {
                findNavController().navigate(ExploreFragmentDirections.actionNavigationExploreToAllFragment(
                    NetworkMovieContainer(it), getString(R.string.toolbar_popular_movies)))
            }
        }

        binding.viewAllButtonTopRated.setOnClickListener {
            viewModel.moviesListTopRated.observe(viewLifecycleOwner) {
                findNavController().navigate(ExploreFragmentDirections.actionNavigationExploreToAllFragment(
                    NetworkMovieContainer(it), getString(R.string.toolbar_top_rated_movies)))
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}