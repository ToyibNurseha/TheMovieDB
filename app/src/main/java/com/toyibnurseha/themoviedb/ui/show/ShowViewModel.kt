package com.toyibnurseha.themoviedb.ui.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.show.TVShowEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository

class ShowViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getPopularShow() : LiveData<List<TVShowEntity>> = repo.getPopularShow()

    fun getEmptyShow() = arrayListOf<MovieEntity>()
}