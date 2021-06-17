package com.toyibnurseha.themoviedb.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.show.TVShowEntity
import com.toyibnurseha.themoviedb.utils.Constant.MOVIE_TABLE_NAME
import com.toyibnurseha.themoviedb.utils.Constant.SHOW_TABLE_NAME
@Dao
interface FavoriteMovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: DetailMovieEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShow(show: DetailShowEntity) : Long

    @Query("SELECT * FROM $MOVIE_TABLE_NAME")
    fun getAllFavoriteMovies() : List<DetailMovieEntity>

    @Query("SELECT * FROM $SHOW_TABLE_NAME")
    fun getAllFavoriteShow() : List<DetailShowEntity>

    @Query("SELECT * FROM $MOVIE_TABLE_NAME where id == :id")
    fun getFavoriteMovie(id: Int) : LiveData<DetailMovieEntity>

    @Query("SELECT * FROM $SHOW_TABLE_NAME where id == :id")
    fun getFavoriteShow(id: Int) : LiveData<DetailShowEntity>

    @Delete
    suspend fun nukeFavoriteMovie(movie: DetailMovieEntity)

    @Delete
    suspend fun nukeFavoriteShow(show: DetailShowEntity)

}