package com.mahmoudhamdyae.themoviedb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mahmoudhamdyae.themoviedb.database.movies.MoviesDao
import com.mahmoudhamdyae.themoviedb.database.movies.MovieRoom
import com.mahmoudhamdyae.themoviedb.database.reviews.ReviewsDao
import com.mahmoudhamdyae.themoviedb.database.reviews.ReviewsRoom
import com.mahmoudhamdyae.themoviedb.database.trailers.TrailersDao
import com.mahmoudhamdyae.themoviedb.database.trailers.TrailersRoom

@Database(
    entities = [
        MovieRoom::class,
        ReviewsRoom::class,
        TrailersRoom::class
    ], version = 1, exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    /**
     * Connects the database to the DAO.
     */
    abstract val movieDao: MoviesDao

    abstract val reviewDao: ReviewsDao

    abstract val trailerDao: TrailersDao
}

private lateinit var INSTANCE: MoviesDatabase

fun getDatabase(context: Context): MoviesDatabase {
    synchronized(MoviesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java,
                "movies"
            ).build()
        }
    }
    return INSTANCE
}