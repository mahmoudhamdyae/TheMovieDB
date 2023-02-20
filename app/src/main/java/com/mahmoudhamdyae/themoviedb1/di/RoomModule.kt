package com.mahmoudhamdyae.themoviedb1.di

//@Module
//@InstallIn(SingletonComponent::class)
//object RoomModule {
//
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): MoviesDatabase {
//        return Room.databaseBuilder(
//            context,
//            MoviesDatabase::class.java,
//            "database"
//        ).build()
//    }
//
//    @Provides
//    fun provideDao(movieDatabase: MoviesDatabase): MovieDao {
//        return movieDatabase.movieDao()
//    }
//}