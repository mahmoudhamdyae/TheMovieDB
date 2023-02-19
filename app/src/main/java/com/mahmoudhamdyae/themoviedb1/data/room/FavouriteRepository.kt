package com.mahmoudhamdyae.themoviedb1.data.room

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import javax.inject.Inject

class FavouriteRepository @Inject constructor(
    private val dao: MovieDao
) {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getUser() = mAuth.currentUser

    private fun getUid() = getUser()?.uid

    fun getFavouriteMovies() = dao.getMovies()

    fun insertFavouriteMovie(movie: Movie) = dao.insertMovie(movie)

    fun deleteFavouriteMovie(movie: Movie) = dao.deleteMovie(movie)



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