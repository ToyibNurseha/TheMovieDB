package com.toyibnurseha.themoviedb.data.source

import androidx.lifecycle.LiveData
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.show.TVShowEntity

interface MovieDataSource {

    fun getPopularMovies() : LiveData<List<MovieEntity>>

    fun getMovieDetail(movieId: Int?) : LiveData<DetailMovieEntity>

    fun getPopularShow() : LiveData<List<TVShowEntity>>

    fun getShowDetail(showId: Int) : LiveData<DetailShowEntity>

    suspend fun insertFavoriteMovie(movie: DetailMovieEntity)

    suspend fun insertFavoriteShow(shot: DetailShowEntity)

    fun getLocalFavoriteMovie() : LiveData<List<DetailMovieEntity>>

    fun getLocalFavoriteShow() : LiveData<List<DetailShowEntity>>

    fun getLocalFavoriteDetailShow() : LiveData<List<DetailShowEntity>>

    fun getFavoriteDetailMovie() : LiveData<List<DetailMovieEntity>>

    fun getFavoriteShow() : LiveData<List<TVShowEntity>>

    fun getFavoriteDetailShow() : LiveData<List<DetailShowEntity>>
}