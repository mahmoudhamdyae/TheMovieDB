package com.mahmoudhamdyae.themoviedb.database.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class], version = 1, exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract fun movieDao() : MovieDao
}

// Use Database

//val db = Room.databaseBuilder(
//    applicationContext,
//    AppDatabase::class.java, "database-name"
//).build()
//
//val userDao = db.userDao()
//val users: List<User> = userDao.getAll()