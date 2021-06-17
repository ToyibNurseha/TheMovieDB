package com.toyibnurseha.themoviedb.ui.favorite

import androidx.lifecycle.ViewModel
import com.toyibnurseha.themoviedb.repository.MovieRepository

class FavoriteViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getFavoriteMovies() = repo.getLocalFavoriteMovie()

    fun getFavoriteShows() = repo.getLocalFavoriteShow()

}