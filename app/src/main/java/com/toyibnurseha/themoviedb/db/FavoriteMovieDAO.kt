package com.toyibnurseha.themoviedb.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.utils.Constant.MOVIE_TABLE_NAME
import com.toyibnurseha.themoviedb.utils.Constant.SHOW_TABLE_NAME
@Dao
interface FavoriteMovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShows(shows: List<TVShowEntity>)

    @Query("SELECT * FROM $MOVIE_TABLE_NAME")
    fun getAllFavoriteMovies() : DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM $SHOW_TABLE_NAME")
    fun getAllFavoriteShow() :  DataSource.Factory<Int, TVShowEntity>

    @Query("SELECT * FROM $MOVIE_TABLE_NAME where id == :id")
    fun getFavoriteMovie(id: Int) : LiveData<MovieEntity>

    @Query("SELECT * FROM $MOVIE_TABLE_NAME where add_watchlist == 1")
    fun getWatchListMovie() : DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM $SHOW_TABLE_NAME where add_watchlist == 1")
    fun getWatchListShows() : DataSource.Factory<Int, TVShowEntity>


    @Query("SELECT * FROM $MOVIE_TABLE_NAME WHERE id = :id")
    fun getMoviesById(id: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM $SHOW_TABLE_NAME WHERE id = :id")
    fun getTvShowsById(id: Int): LiveData<TVShowEntity>

    @Update
    fun updateMovies(movies: MovieEntity)

    @Update
    fun updateTvShows(tvShow: TVShowEntity)
}