package com.mahmoudhamdyae.themoviedb1

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mahmoudhamdyae.themoviedb1.adapters.AllAdapter
import com.mahmoudhamdyae.themoviedb1.adapters.ReviewsAdapter
import com.mahmoudhamdyae.themoviedb1.adapters.TrailersAdapter
import com.mahmoudhamdyae.themoviedb1.adapters.MovieExploreAdapter
import com.mahmoudhamdyae.themoviedb1.models.Movie
import com.mahmoudhamdyae.themoviedb1.models.Review
import com.mahmoudhamdyae.themoviedb1.models.Trailer

/**
 * When there is no Movie property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("moviesExploreListData")
fun bindMoviesExploreRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieExploreAdapter
    adapter.submitList(data)
}

@BindingAdapter("moviesListData")
fun bindMoviesRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as AllAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl.let {
        val imgUrlFull  = "http://image.tmdb.org/t/p/w342/${imgUrl ?: ""}"
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
fun bindReviewsRecyclerView(recyclerView: RecyclerView, data: List<Review>?) {
    val adapter = recyclerView.adapter as ReviewsAdapter
    adapter.submitList(data)
}

// Trailers

@BindingAdapter("trailersData")
fun bindTrailersRecyclerView(recyclerView: RecyclerView, data: List<Trailer>?) {
    val adapter = recyclerView.adapter as TrailersAdapter
    adapter.submitList(data)
}