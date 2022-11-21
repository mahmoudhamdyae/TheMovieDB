package com.mahmoudhamdyae.themoviedb1.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahmoudhamdyae.themoviedb1.database.network.Movie

@Database(
    entities = [Movie::class], version = 1, exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract fun movieDao() : MovieDao
}