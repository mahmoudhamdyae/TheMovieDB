package com.mahmoudhamdyae.themoviedb1.di

import com.google.firebase.auth.FirebaseAuth
import com.mahmoudhamdyae.themoviedb1.data.network.ApiService
import com.mahmoudhamdyae.themoviedb1.data.repository.Repository
import com.mahmoudhamdyae.themoviedb1.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(mAuth: FirebaseAuth, apiService: ApiService): Repository {
        return RepositoryImpl(mAuth, apiService)
    }
}