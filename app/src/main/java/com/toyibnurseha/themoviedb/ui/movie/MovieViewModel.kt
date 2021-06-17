package com.toyibnurseha.themoviedb.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getPopularMovies() : LiveData<List<MovieEntity>> = repo.getPopularMovies()

    fun getEmptyMovie() = arrayListOf<MovieEntity>()

}