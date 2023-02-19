package com.mahmoudhamdyae.themoviedb1.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mahmoudhamdyae.themoviedb1.R
import com.mahmoudhamdyae.themoviedb1.data.models.NetworkMovieContainer
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentFavouriteBinding
import com.mahmoudhamdyae.themoviedb1.ui.explore.MovieExploreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment: Fragment() {

    private val viewModel : FavouriteViewModel by viewModels()
    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Toolbar
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbar.inflateMenu(R.menu.favourites_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sign_out -> {
                    signOut()
                    true
                }
                else -> false
            }
        }

        // Log In
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user == null) {
                binding.scrollView.visibility = View.GONE
            } else {
                binding.logIn.visibility = View.GONE
            }
        }
        binding.logInButton.setOnClickListener {
            launchSignInFlow()
        }

        // Movies RecyclerView
        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewMovies.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToDetailFragment(it))
        })

        // TV Shows RecyclerView
        binding.recyclerViewTvShows.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewTvShows.adapter = MovieExploreAdapter(MovieExploreAdapter.OnClickListener {
            findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToDetailFragment(it))
        })

        binding.viewAllButtonMovies.setOnClickListener {
            val movies = NetworkMovieContainer(viewModel.movies.value)
            findNavController().navigate(FavouriteFragmentDirections.actionNavigationFavouriteToAllFragment(movies, getString(R.string.toolbar_favourite_movies)))
        }
        binding.viewAllButtonTvShows.setOnClickListener {
            val tvShows = NetworkMovieContainer(viewModel.tvShows.value)
            findNavController().navigate(FavouriteFragmentDirections.actionNavigationFavouriteToAllFragment(tvShows, getString(R.string.toolbar_favourite_tv_shows)))
        }

        // Toast Error Message
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Show Bottom Navigation View
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        view.visibility = View.VISIBLE

        viewModel.getFavourites()
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun launchSignInFlow() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .setLogo(R.drawable.ic_launcher_foreground)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            // Successfully signed in
            binding.logIn.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE

            viewModel.getCurrentUser()
        } else {
            // Sign in failed.
            Toast.makeText(context, response?.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(requireContext())
            .addOnCompleteListener {
                launchSignInFlow()
            }
    }
}