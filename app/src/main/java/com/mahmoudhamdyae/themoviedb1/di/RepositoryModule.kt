package com.mahmoudhamdyae.themoviedb1.di

import com.mahmoudhamdyae.themoviedb1.data.repository.Repository
import com.mahmoudhamdyae.themoviedb1.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(impl: RepositoryImpl): Repository
}