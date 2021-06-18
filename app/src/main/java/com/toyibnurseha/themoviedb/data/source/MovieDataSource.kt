package com.toyibnurseha.themoviedb.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.utils.Resource

interface MovieDataSource {

    fun getPopularMovies() : LiveData<Resource<PagedList<MovieEntity>>>

    fun getPopularShow() : LiveData<Resource<PagedList<TVShowEntity>>>

    fun setMoviesWatchlist(movies: MovieEntity, state: Boolean)

    fun setTvShowsWatchlist(tvShows: TVShowEntity, state: Boolean)

    fun getMoviesWatchlist(): LiveData<PagedList<MovieEntity>>

    fun getTvShowsWatchlist(): LiveData<PagedList<TVShowEntity>>

    fun loadMoviesDetails(moviesID: Int): LiveData<Resource<MovieEntity>>

    fun loadTvShowsDetails(tvShowsID: Int): LiveData<Resource<TVShowEntity>>
}