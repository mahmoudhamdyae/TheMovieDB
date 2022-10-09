package com.mahmoudhamdyae.themoviedb.database.movies

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movieroom")
    fun getMovies(): LiveData<List<MovieRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: MovieRoom)

    @Query("DELETE FROM movieroom")
    fun clear()
}