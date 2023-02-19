package com.mahmoudhamdyae.themoviedb1.data.room

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import javax.inject.Inject

class FavouriteRepository @Inject constructor(
    private val dao: MovieDao
) {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getUid() = mAuth.currentUser!!.uid

    // Auth

    fun signUp(email: String, password: String): Task<AuthResult> {
        return mAuth.createUserWithEmailAndPassword(email, password)
    }

    fun logIn(email: String, password: String): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(email, password)
    }

    fun getFavouriteMovies() = dao.getMovies()

    fun insertFavouriteMovie(movie: Movie) = dao.insertMovie(movie)

    fun deleteFavouriteMovie(movie: Movie) = dao.deleteMovie(movie)
}