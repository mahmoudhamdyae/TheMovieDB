package com.mahmoudhamdyae.themoviedb1.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.models.Review
import com.mahmoudhamdyae.themoviedb1.data.models.Trailer
import com.mahmoudhamdyae.themoviedb1.data.network.ApiService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val mAuth: FirebaseAuth,
    private val apiService: ApiService
): Repository {

    // Movies

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return apiService.getPopularMoviesAsync(page).await().results
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        return apiService.getTopRatedMoviesAsync(page).await().results
    }

    override suspend fun getSearchedMovies(query: String): List<Movie> {
        return apiService.getMovieSearchAsync(query).await().results
    }

    override suspend fun getReviews(movieId: String): List<Review> {
        return apiService.getReviewsAsync(movieId).await().results
    }

    override suspend fun getTrailers(movieId: String): List<Trailer> {
        return apiService.getTrailersAsync(movieId).await().results
    }

    // TV Shows

    override suspend fun getPopularTVShows(page: Int): List<Movie> {
        return apiService.getPopularTVShowsAsync(page).await().results
    }

    override suspend fun getTopRatedTVShows(page: Int): List<Movie> {
        return apiService.getTopRatedTVShowsAsync(page).await().results
    }

    override suspend fun getSearchedTVShows(query: String): List<Movie> {
        return apiService.getTVShowSearchAsync(query).await().results
    }

    override suspend fun getTVReviews(movieId: String): List<Review> {
        return apiService.getTVReviewsAsync(movieId).await().results
    }

    override suspend fun getTVTrailers(movieId: String): List<Trailer> {
        return apiService.getTVTrailersAsync(movieId).await().results
    }

    // Favourites

    override fun getUser() = mAuth.currentUser

    private fun getUid() = getUser()?.uid

    private val db = Firebase.firestore

    override fun getMoviesFromFirebase(): Task<QuerySnapshot> {
        return db.collection(getUid()!!).get()
    }

    override fun addMovieToFirebase(movie: Movie): Task<Void> {
        val data = hashMapOf(
            "id" to movie.id,
            "title" to movie.title,
            "name" to movie.name,
            "posterPath" to movie.posterPath,
            "overview" to movie.overview,
            "userRating" to movie.userRating,
            "releaseDate" to movie.releaseDate
        )
        return db.collection(getUid()!!).document(movie.id).set(data)
    }

    override fun delMovieFromFirebase(movieId: String): Task<Void> {
        return db.collection(getUid()!!).document(movieId).delete()
    }
}