package com.toyibnurseha.themoviedb.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository
import com.toyibnurseha.themoviedb.utils.Resource

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getMovies() : LiveData<Resource<PagedList<MovieEntity>>> = repo.getPopularMovies()

    fun getEmptyMovie() = arrayListOf<MovieEntity>()

}