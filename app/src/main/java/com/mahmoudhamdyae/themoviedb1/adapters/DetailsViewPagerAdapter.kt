package com.mahmoudhamdyae.themoviedb1.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.ui.ReviewsFragment
import com.mahmoudhamdyae.themoviedb1.ui.SummaryFragment
import com.mahmoudhamdyae.themoviedb1.ui.TrailersFragment

/**
 * The Adapter of the view pager
 */
class DetailsViewPagerAdapter(
    fragment: Fragment,
    val movie: Movie,
    private val isMovie: Boolean
    ) : FragmentStateAdapter(fragment) {

    /**
     * Counts total number of tabs
     */
    override fun getItemCount(): Int = 3

    /**
     * Adds Fragment to UI
     */
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SummaryFragment(movie.overview)
            1 -> ReviewsFragment(movie, isMovie)
            else -> TrailersFragment(movie, isMovie)
        }
    }
}