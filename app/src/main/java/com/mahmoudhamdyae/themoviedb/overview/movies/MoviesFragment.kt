package com.mahmoudhamdyae.themoviedb.overview.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mahmoudhamdyae.themoviedb.R
import com.mahmoudhamdyae.themoviedb.databinding.FragmentMoviesBinding
import com.mahmoudhamdyae.themoviedb.database.network.Movie
import com.mahmoudhamdyae.themoviedb.database.network.NetworkMovieContainer
import com.mahmoudhamdyae.themoviedb.overview.MovieAdapter
import com.mahmoudhamdyae.themoviedb.overview.OverviewFragmentDirections

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
        val viewModelFactory = MoviesViewModelFactory(1, requireActivity().application)
        val viewModel : MoviesViewModel = ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java]

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Calculate number of columns
        val noOfColumns = getNoOfColumns()
        binding.photosGrid.layoutManager = GridLayoutManager(context, noOfColumns)
//        binding.photosGrid.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.photosGrid.adapter = MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer(fun(movie : Movie?) {
            if (null != movie) {
                findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(movie))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        }))

        binding.viewAllButton.setOnClickListener {
            viewModel.list.observe(viewLifecycleOwner) {
                findNavController().navigate(OverviewFragmentDirections.actionNavigationOverviewToAllFragment(it))
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        view.visibility = View.VISIBLE
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