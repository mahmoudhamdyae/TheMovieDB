package com.mahmoudhamdyae.themoviedb.database.tvshows

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TVShowDao {
    @Query("SELECT * FROM tvshowsroom")
    fun getTVShows(): LiveData<List<TVShowsRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tvShows: TVShowsRoom)

    @Query("DELETE FROM tvshowsroom")
    fun clear()
}