package com.mahmoudhamdyae.themoviedb.overview

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mahmoudhamdyae.themoviedb.overview.movies.MoviesFragment
import com.mahmoudhamdyae.themoviedb.overview.tvshows.TVShowsFragment

/**
 * The Adapter of the view pager
 */
class OverviewViewPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Counts total number of tabs
     */
    override fun getItemCount(): Int = 2

    /**
     * Adds Fragment to UI
     */
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MoviesFragment()
            else -> TVShowsFragment()
        }
    }
}