package com.mahmoudhamdyae.themoviedb1.database.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudhamdyae.themoviedb1.database.network.Movie
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MoviesDatabaseTest {

    private lateinit var db: MoviesDatabase
    private lateinit var dao: MovieDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MoviesDatabase::class.java).build()
        dao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadMovies() {
        val movie = Movie(
            "1",
            "title1",
            "name1",
            "realname1",
            "posterPath1",
            "overview1",
            "userRating1",
            "releaseDate1"
        )
        dao.insertMovie(movie)
        val movies = dao.getMovies()
//        assert(movies.contains(movie))
    }

    @Test
    fun delMovie() {
        val movie = Movie(
            "1",
            "title1",
            "name1",
            "realname1",
            "posterPath1",
            "overview1",
            "userRating1",
            "releaseDate1"
        )
        dao.insertMovie(movie)
        dao.deleteMovie(movie)
        val movies = dao.getMovies()
//        assert(!movies.contains(movie))
    }
}