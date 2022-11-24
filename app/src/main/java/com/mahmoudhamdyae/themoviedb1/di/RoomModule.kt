package com.mahmoudhamdyae.themoviedb1.di

import android.content.Context
import androidx.room.Room
import com.mahmoudhamdyae.themoviedb1.data.room.MovieDao
import com.mahmoudhamdyae.themoviedb1.data.room.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideDao(movieDatabase: MoviesDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            "database"
        ).build()
    }
}