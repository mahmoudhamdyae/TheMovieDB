package com.mahmoudhamdyae.themoviedb1.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import com.mahmoudhamdyae.themoviedb1.data.models.Review
import com.mahmoudhamdyae.themoviedb1.data.models.Trailer

interface Repository {

    // Movies
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getTopRatedMovies(page: Int): List<Movie>
    suspend fun getSearchedMovies(query: String): List<Movie>
    suspend fun getReviews(movieId: String): List<Review>
    suspend fun getTrailers(movieId: String): List<Trailer>

    // TV Shows
    suspend fun getPopularTVShows(page: Int): List<Movie>
    suspend fun getTopRatedTVShows(page: Int): List<Movie>
    suspend fun getSearchedTVShows(query: String): List<Movie>
    suspend fun getTVReviews(movieId: String): List<Review>
    suspend fun getTVTrailers(movieId: String): List<Trailer>

    // Favourites
    fun getUser(): FirebaseUser?
    fun getMoviesFromFirebase(): Task<QuerySnapshot>
    fun addMovieToFirebase(movie: Movie): Task<Void>
    fun delMovieFromFirebase(movieId: String): Task<Void>
}