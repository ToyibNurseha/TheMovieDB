package com.toyibnurseha.themoviedb.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository

class FavoriteViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getMoviesWatchlist(): LiveData<PagedList<MovieEntity>> = repo.getMoviesWatchlist()

    fun getTvShowsWatchlist(): LiveData<PagedList<TVShowEntity>> = repo.getTvShowsWatchlist()

    fun getEmptyShowWatchList() = arrayListOf<TVShowEntity>()

    fun getEmptyMovieWatchList() = arrayListOf<MovieEntity>()
}