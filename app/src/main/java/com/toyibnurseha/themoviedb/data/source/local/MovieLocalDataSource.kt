package com.toyibnurseha.themoviedb.data.source.local

import androidx.lifecycle.LiveData
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.show.TVShowEntity
import com.toyibnurseha.themoviedb.db.MovieDatabase
import com.toyibnurseha.themoviedb.utils.EspressoIdlingResource

class MovieLocalDataSource(val db: MovieDatabase) {

    companion object {

        @Volatile
        private var instance: MovieLocalDataSource? = null

        fun getInstance(db: MovieDatabase): MovieLocalDataSource =
            instance ?: synchronized(this) {
                MovieLocalDataSource(db).apply { instance = this }
            }
    }

    fun getFavoriteMovies(callback: LoadFavoriteMovieCallback) {
        EspressoIdlingResource.increment()
        db.getFavoriteMovie().getAllFavoriteMovies().let {
            callback.responseFavoriteMovie(it)
            EspressoIdlingResource.decrement()
        }
    }

    fun getFavoriteShow(callback: LoadFavoriteShowCallback) {
        EspressoIdlingResource.increment()
        db.getFavoriteMovie().getAllFavoriteShow().let {
            callback.responsePopularShow(it)
            EspressoIdlingResource.decrement()
        }
    }

    interface LoadFavoriteMovieCallback {
        fun responseFavoriteMovie(response: List<DetailMovieEntity>)
    }

    interface LoadFavoriteMovieDetailCallback {
        fun responseFavoriteMovieDetail(response: DetailMovieEntity)
    }

    interface LoadFavoriteShowCallback {
        fun responsePopularShow(response: List<DetailShowEntity>)
    }

    interface LoadFavoriteShowDetailCallback {
        fun responseDetailShow(response: DetailShowEntity)
    }

}