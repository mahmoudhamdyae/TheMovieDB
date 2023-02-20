package com.mahmoudhamdyae.themoviedb1.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.network.ApiService
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService
) {

    // Movies

    suspend fun getPopularMovies(page: Int) =
        apiService.getPopularMoviesAsync(page).await().results

    suspend fun getTopRatedMovies(page: Int) =
        apiService.getTopRatedMoviesAsync(page).await().results

    suspend fun getSearchedMovies(query: String) =
        apiService.getMovieSearchAsync(query).await().results

    suspend fun getReviews(movieId: String) =
        apiService.getReviewsAsync(movieId).await().results

    suspend fun getTrailers(movieId: String) =
        apiService.getTrailersAsync(movieId).await().results

    // TV Shows

    suspend fun getPopularTVShows(page: Int) =
        apiService.getPopularTVShowsAsync(page).await().results

    suspend fun getTopRatedTVShows(page: Int) =
        apiService.getTopRatedTVShowsAsync(page).await().results

    suspend fun getSearchedTVShows(query: String) =
        apiService.getTVShowSearchAsync(query).await().results

    suspend fun getTVReviews(movieId: String) =
        apiService.getTVReviewsAsync(movieId).await().results

    suspend fun getTVTrailers(movieId: String) =
        apiService.getTVTrailersAsync(movieId).await().results

    // Favourites

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getUser() = mAuth.currentUser

    private fun getUid() = getUser()?.uid

    private val db = Firebase.firestore

    fun getMoviesFromFirebase(): Task<QuerySnapshot> {
        return db.collection(getUid()!!).get()
    }

    fun addMovieToFirebase(movie: Movie): Task<Void> {
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

    fun delMovieFromFirebase(movieId: String): Task<Void> {
        return db.collection(getUid()!!).document(movieId).delete()
    }
}