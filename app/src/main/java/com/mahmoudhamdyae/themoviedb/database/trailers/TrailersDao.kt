package com.mahmoudhamdyae.themoviedb.database.trailers

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrailersDao {
    @Query("SELECT * FROM trailersroom")
    fun getTrailers(): LiveData<List<TrailersRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg trailers: TrailersRoom)
}