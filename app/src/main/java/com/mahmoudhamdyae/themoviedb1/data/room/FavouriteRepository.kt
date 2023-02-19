package com.mahmoudhamdyae.themoviedb1.data.room

import com.google.firebase.auth.FirebaseAuth
import com.mahmoudhamdyae.themoviedb1.data.models.Movie
import javax.inject.Inject

class FavouriteRepository @Inject constructor(
    private val dao: MovieDao
) {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getUser() = mAuth.currentUser

    fun getUid() = getUser()?.uid

    fun getFavouriteMovies() = dao.getMovies()

    fun insertFavouriteMovie(movie: Movie) = dao.insertMovie(movie)

    fun deleteFavouriteMovie(movie: Movie) = dao.deleteMovie(movie)

//    private val userId = Utils().getUser()?.uid.toString()
//    private val db = Firebase.firestore
//
//    fun getAllNotes() = db.collection(userId)
//
//    fun saveNote(note: Note) {
//        val data = hashMapOf(
//            "title" to note.title,
//            "description" to note.description
//        )
//        db.collection(userId)
//            .add(data)
//            .addOnSuccessListener {document ->
//                Log.d("haha", "DocumentSnapshot written with ID: ${document.id}")
//            }
//            .addOnFailureListener { e -> Log.w("haha", "Error writing document", e) }
//    }
//
//    fun updateNote(note: Note) {
//        val data = hashMapOf(
//            "title" to note.title,
//            "description" to note.description
//        )
//        db.collection(userId).document(note.id)
//            .set(data)
//            .addOnSuccessListener {
//                Log.d("haha", "DocumentSnapshot successfully updated!")
//            }
//            .addOnFailureListener { e ->
//                Log.w("haha", "Error updating document", e)
//            }
//    }
//
//    fun delNote(noteId: String) {
//        db.collection(userId).document(noteId)
//            .delete()
//            .addOnSuccessListener {
//                Log.d("haha", "Document Deleted")
//            }
//            .addOnFailureListener { e ->
//                Log.w("haha", "Error deleting document", e)
//            }
//    }
}