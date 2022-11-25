package com.mahmoudhamdyae.themoviedb1.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.mahmoudhamdyae.themoviedb1.R
import com.mahmoudhamdyae.themoviedb1.adapters.ExploreViewPageAdapter
import com.mahmoudhamdyae.themoviedb1.databinding.FragmentExploreBinding


@Suppress("DEPRECATION")
class ExploreFragment : Fragment() {

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the ExploreFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentExploreBinding.inflate(inflater)

        // Tabs

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        val adapter = ExploreViewPageAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_movies)
                else -> getString(R.string.tab_tv_shows)
            }

            tab.setIcon(when (position) {
                0 -> R.drawable.ic_baseline_movie_24
                else -> R.drawable.ic_baseline_live_tv_24
            })
        }.attach()

        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        view.visibility = View.VISIBLE
    }
}