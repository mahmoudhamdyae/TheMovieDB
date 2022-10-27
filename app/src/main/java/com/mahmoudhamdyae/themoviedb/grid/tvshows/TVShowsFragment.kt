package com.mahmoudhamdyae.themoviedb.grid.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.themoviedb.databinding.FragmentTvShowsBinding
import com.mahmoudhamdyae.themoviedb.domain.Movie
import com.mahmoudhamdyae.themoviedb.domain.TVShow

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
//        val binding = FragmentTvShowsBinding.inflate(inflater)
        _binding = FragmentTvShowsBinding.inflate(inflater, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Initialize [MoviesViewModel].
        val viewModel : TVShowsViewModel = ViewModelProvider(this)[TVShowsViewModel::class.java]

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Calculate number of columns
        val noOfColumns = getNoOfColumns()
        binding.photosGrid.layoutManager = GridLayoutManager(context, noOfColumns)

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.photosGrid.adapter = TVShowAdapter(TVShowAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedTVShow.observe(viewLifecycleOwner, Observer(fun(tvShow : TVShow?) {
            if (null != tvShow) {
                findNavController().navigate(TVShowsFragmentDirections.actionTvShowFragmentToDetailFragment(
                    Movie(
                        tvShow.id,
                        tvShow.title,
                        tvShow.posterPath,
                        tvShow.overview,
                        tvShow.userRating,
                        ""
                    ),
                    false))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        }))

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getNoOfColumns(): Int {
        val displayMetrics = requireContext().resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return kotlin.math.ceil(screenWidthDp / 185f).toInt()
    }
}