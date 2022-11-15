package com.mahmoudhamdyae.themoviedb

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mahmoudhamdyae.themoviedb.database.network.Movie
import com.mahmoudhamdyae.themoviedb.overview.tvshows.TVShowAdapter
import com.mahmoudhamdyae.themoviedb.detail.reviews.ReviewsAdapter
import com.mahmoudhamdyae.themoviedb.detail.trailers.TrailersAdapter
import com.mahmoudhamdyae.themoviedb.overview.movies.MovieAdapter
import com.mahmoudhamdyae.themoviedb.database.network.NetworkReview
import com.mahmoudhamdyae.themoviedb.database.network.NetworkTrailer
import com.mahmoudhamdyae.themoviedb.database.network.TVShow

/**
 * When there is no Movie property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("moviesListData")
fun bindMoviesRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieAdapter
    adapter.submitList(data)
}

@BindingAdapter("TVShowsListData")
fun bindTVShowsRecyclerView(recyclerView: RecyclerView, data: List<TVShow>?) {
    val adapter = recyclerView.adapter as TVShowAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUrlFull = "http://image.tmdb.org/t/p/w342/$imgUrl"
        val imgUri = imgUrlFull.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("movieApiStatus")
fun bindStatus(statusImageView: ImageView, status: MovieApiStatus?) {
    when (status) {
        MovieApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MovieApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MovieApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {}
    }
}

// Reviews

@BindingAdapter("reviewsData")
fun bindReviewsRecyclerView(recyclerView: RecyclerView, data: List<NetworkReview>?) {
    val adapter = recyclerView.adapter as ReviewsAdapter
    adapter.submitList(data)
}

// Trailers

@BindingAdapter("trailersData")
fun bindTrailersRecyclerView(recyclerView: RecyclerView, data: List<NetworkTrailer>?) {
    val adapter = recyclerView.adapter as TrailersAdapter
    adapter.submitList(data)
}