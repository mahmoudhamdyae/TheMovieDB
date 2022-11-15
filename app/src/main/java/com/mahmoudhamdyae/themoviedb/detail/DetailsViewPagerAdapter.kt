package com.mahmoudhamdyae.themoviedb.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mahmoudhamdyae.themoviedb.detail.reviews.ReviewsFragment
import com.mahmoudhamdyae.themoviedb.detail.summary.SummaryFragment
import com.mahmoudhamdyae.themoviedb.detail.trailers.TrailersFragment
import com.mahmoudhamdyae.themoviedb.database.network.Movie

/**
 * The Adapter of the view pager
 */
class DetailsViewPagerAdapter(fragment: Fragment, val movie: Movie, val isMovie: Boolean) : FragmentStateAdapter(fragment) {

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
            1 -> ReviewsFragment(movie.id, isMovie)
            else -> TrailersFragment(movie.id, isMovie)
        }
    }
}