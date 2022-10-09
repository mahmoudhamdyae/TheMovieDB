package com.mahmoudhamdyae.themoviedb.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.themoviedb.database.movies.asDomainModel
import com.mahmoudhamdyae.themoviedb.database.reviews.asDomainModel
import com.mahmoudhamdyae.themoviedb.database.trailers.asDomainModel
import com.mahmoudhamdyae.themoviedb.detail.reviews.ReviewsViewModel
import com.mahmoudhamdyae.themoviedb.detail.reviews.ReviewsViewModelFactory
import com.mahmoudhamdyae.themoviedb.domain.Movie
import com.mahmoudhamdyae.themoviedb.domain.Review
import com.mahmoudhamdyae.themoviedb.domain.Trailer
import com.mahmoudhamdyae.themoviedb.network.MovieApi
import com.mahmoudhamdyae.themoviedb.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: MoviesDatabase) {

    /**
     * A playlist of movies that can be shown on the screen.
     */
    val movies: LiveData<List<Movie>> =
        Transformations.map(database.movieDao.getMovies()) {
            it.asDomainModel()
        }

//    private var movieID : String = "718930"
    /**
    * A playlist of reviews that can be shown on the screen.
    */
    val reviews: LiveData<List<Review>> =
        Transformations.map(database.reviewDao.getReviews(/*movieID*/)) {
//            Log.i("Database","movieID $movieID ${database.reviewDao.getReviews(movieID).value?.get(0)?.author}")
            it.asDomainModel()
        }

    /**
     * A playlist of trailers that can be shown on the screen.
     */
    val trailers: LiveData<List<Trailer>> =
        Transformations.map(database.trailerDao.getTrailers()) {
            it.asDomainModel()
        }

    /**
     * Refresh the movies stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the movies for use, observe [movies]
     */
    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val moviesList = MovieApi.retrofitService.getPopularMoviesAsync().await()
            val moviesDao = database.movieDao
            moviesDao.clear()
            moviesDao.insertAll(*moviesList.asDatabaseModel())
        }
    }

    suspend fun refreshReviews(movieId: String) {
        withContext(Dispatchers.IO) {
//            movieID = movieId
            val reviewsList = MovieApi.retrofitService.getReviewsAsync(movieId).await()
            database.reviewDao.insertAll(*reviewsList.asDatabaseModel())
        }
    }

//    suspend fun refreshTrailers(movieId: String) {
//        withContext(Dispatchers.IO) {
//            val trailersList = MovieApi.retrofitService.getTrailersAsync(movieId).await()
//            database.trailerDao.insertAll(*trailersList.asDatabaseModel())
//        }
//    }

}
