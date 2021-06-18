package com.toyibnurseha.themoviedb.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.db.FavoriteMovieDAO

class MovieLocalDataSource(private val movieDao: FavoriteMovieDAO) {

    companion object {

        @Volatile
        private var instance: MovieLocalDataSource? = null

        fun getInstance(dao: FavoriteMovieDAO): MovieLocalDataSource =
            instance ?: synchronized(this) {
                MovieLocalDataSource(dao).apply { instance = this }
            }
    }

    fun getFavoriteMovies() : DataSource.Factory<Int, MovieEntity> = movieDao.getAllFavoriteMovies()

    fun getFavoriteShows() : DataSource.Factory<Int, TVShowEntity> = movieDao.getAllFavoriteShow()

    fun getWatchListMovie() : DataSource.Factory<Int, MovieEntity> = movieDao.getWatchListMovie()

    fun getWatchListShows() : DataSource.Factory<Int, TVShowEntity> = movieDao.getWatchListShows()

    fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    fun insertTvShows(tvShows: List<TVShowEntity>) = movieDao.insertShows(tvShows)

    fun getMoviesById(moviesID: Int): LiveData<MovieEntity> = movieDao.getMoviesById(moviesID)

    fun getTvShowsById(tvShowsId: Int): LiveData<TVShowEntity> = movieDao.getTvShowsById(tvShowsId)

    fun updateMoviesWatchlist(movies: MovieEntity, newState: Boolean) {
        movies.addWatchlist = newState
        movieDao.updateMovies(movies)
    }

    fun updateTvShowsWatchlist(tvShows: TVShowEntity, newState: Boolean) {
        tvShows.addWatchlist = newState
        movieDao.updateTvShows(tvShows)
    }


}