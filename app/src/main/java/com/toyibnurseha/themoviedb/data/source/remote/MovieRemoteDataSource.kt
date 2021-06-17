package com.toyibnurseha.themoviedb.data.source.remote

import com.toyibnurseha.themoviedb.api.RetrofitInstance
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.show.TVShowEntity
import com.toyibnurseha.themoviedb.utils.EspressoIdlingResource
import retrofit2.await

class MovieRemoteDataSource {

    companion object {

        @Volatile
        private var instance: MovieRemoteDataSource? = null

        fun getInstance(): MovieRemoteDataSource =
            instance ?: synchronized(this) {
                MovieRemoteDataSource().apply { instance = this }
            }
    }

    suspend fun getPopularMovies(callback: LoadPopularMovieCallback) {
        EspressoIdlingResource.increment()
        val instanceApiConfig = RetrofitInstance.api
        instanceApiConfig.getPopularMovies().await().results.let { movies ->
            callback.responsePopularMovie(
                movies
            )
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getMovieDetail(movieId: Int, callback: LoadMovieDetailCallback) {
        EspressoIdlingResource.increment()
        val instanceApiConfig = RetrofitInstance.api
        instanceApiConfig.getDetailMovie(movieId.toInt()).await().let { movie ->
            callback.responseMovieDetail(
                movie
            )
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getPopularShows(callback: LoadShowCallback) {
        EspressoIdlingResource.increment()
        val instanceApiConfig = RetrofitInstance.api
        instanceApiConfig.getPopularShow().await().results.let { showList ->
            callback.responsePopularShow(
                showList
            )
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getShowDetail(showId: Int, callback: LoadShowDetailCallback) {
        EspressoIdlingResource.increment()
        val instanceApiConfig = RetrofitInstance.api
        instanceApiConfig.getDetailShow(showId).await().let { tvShow ->
            callback.responseDetailShow(
                tvShow
            )
            EspressoIdlingResource.decrement()
        }
    }

    interface LoadPopularMovieCallback {
        fun responsePopularMovie(response: List<MovieEntity>)
    }

    interface LoadMovieDetailCallback {
        fun responseMovieDetail(response: DetailMovieEntity)
    }

    interface LoadShowCallback {
        fun responsePopularShow(response: List<TVShowEntity>)
    }

    interface LoadShowDetailCallback {
        fun responseDetailShow(response: DetailShowEntity)
    }
}