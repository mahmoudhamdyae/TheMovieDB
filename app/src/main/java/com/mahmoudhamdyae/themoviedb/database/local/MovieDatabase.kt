package com.mahmoudhamdyae.themoviedb.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mahmoudhamdyae.themoviedb.database.network.Movie

@Database(
    entities = [Movie::class], version = 1, exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract fun movieDao() : MovieDao
}