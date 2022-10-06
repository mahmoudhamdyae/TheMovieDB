package com.mahmoudhamdyae.themoviedb.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.themoviedb.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment () {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMoviesBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

//        val viewModelFactory = MoviesViewModelFactory(requireActivity().application)
//        val viewModel = ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java]

        // Initialize [MoviesViewModel].
        val viewModel : MoviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Calculate number of columns
        val noOfColumns = getNoOfColumns()
        binding.photosGrid.layoutManager = GridLayoutManager(context, noOfColumns)

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.photosGrid.adapter = MoviesAdapter(MoviesAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                // TODO Safe args
                findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToDetailFragment(/*it*/))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        })

        // Handle Network is not available
//        viewModel.toastNetwork.observe(viewLifecycleOwner, Observer {
//            if (null != it) {
//                Toast.makeText(context,it, Toast.LENGTH_SHORT).show()
//                viewModel.clearToast()
//            }
//        })

        return binding.root
    }

    private fun getNoOfColumns(): Int {
        val displayMetrics = requireContext().resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return kotlin.math.ceil(screenWidthDp / 185f).toInt()
    }
}