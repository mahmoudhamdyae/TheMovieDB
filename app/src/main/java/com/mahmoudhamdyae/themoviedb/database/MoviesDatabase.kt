package com.mahmoudhamdyae.themoviedb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mahmoudhamdyae.themoviedb.database.movies.MoviesDao
import com.mahmoudhamdyae.themoviedb.database.movies.MovieRoom
import com.mahmoudhamdyae.themoviedb.database.tvshows.TVShowDao
import com.mahmoudhamdyae.themoviedb.database.tvshows.TVShowsRoom

@Database(
    entities = [
        MovieRoom::class, TVShowsRoom::class
    ], version = 1, exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    /**
     * Connects the database to the DAO.
     */
    abstract val movieDao: MoviesDao
    abstract val tvShowsDao: TVShowDao
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