package com.mahmoudhamdyae.themoviedb.database.reviews

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReviewsDao {
//    @Query("SELECT * FROM reviewsroom WHERE id == :movieId")
    @Query("SELECT * FROM reviewsroom")
    fun getReviews(/*movieId: String*/): LiveData<List<ReviewsRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg reviews: ReviewsRoom)
}