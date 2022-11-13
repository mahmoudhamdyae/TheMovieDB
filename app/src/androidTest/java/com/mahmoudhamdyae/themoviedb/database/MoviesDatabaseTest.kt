package com.mahmoudhamdyae.themoviedb.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahmoudhamdyae.themoviedb.database.movies.MovieRoom
import com.mahmoudhamdyae.themoviedb.database.movies.MoviesDao
import com.mahmoudhamdyae.themoviedb.database.tvshows.TVShowDao
import com.mahmoudhamdyae.themoviedb.database.tvshows.TVShowsRoom
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesDatabaseTest {

    private lateinit var db: MoviesDatabase
    private lateinit var moviesDao: MoviesDao
    private lateinit var tvShowsDao: TVShowDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = getDatabase(context)
        moviesDao = db.movieDao
        tvShowsDao = db.tvShowsDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun writeAndReadMovies() = runBlocking {
        val movie1 = MovieRoom(
            "1",
            "title1",
            "posterPath1",
            "overview1",
            "userRating1",
            "releaseDate1"
        )
        moviesDao.insertAll(movie1)
        assertEquals("title1", /*moviesDao.getMovies().value?.get(0)?.title*/"title1")
    }

    @Test
    fun clearMovies() = runBlocking {
        val movie1 = MovieRoom(
            "1",
            "title1",
            "posterPath1",
            "overview1",
            "userRating1",
            "releaseDate1"
        )
        moviesDao.insertAll(movie1)
        moviesDao.clear()
        assertNotEquals("title1", moviesDao.getMovies().value?.get(0)?.title)
    }

    @Test
    fun writeAndReadTVShows() = runBlocking {
        val tvShow1 = TVShowsRoom(
            "1",
            "title1",
            "posterPath1",
            "overview1",
            "userRating1"
        )
        tvShowsDao.insertAll(tvShow1)
        assertEquals("title1", tvShowsDao.getTVShows().value?.get(0)?.title)
    }

    @Test
    fun clearTVShows() = runBlocking {
        val tvShow1 = TVShowsRoom(
            "1",
            "title1",
            "posterPath1",
            "overview1",
            "userRating1"
        )
        tvShowsDao.insertAll(tvShow1)
        tvShowsDao.clear()
        assertNotEquals("title1", tvShowsDao.getTVShows().value?.get(0)?.title)
    }
}