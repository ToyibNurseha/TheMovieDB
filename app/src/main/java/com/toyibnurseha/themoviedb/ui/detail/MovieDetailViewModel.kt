package com.toyibnurseha.themoviedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getDetailMovie(movieId: Int) : LiveData<DetailMovieEntity> = repo.getMovieDetail(movieId)

    fun getDetailShow(showId: Int) : LiveData<DetailShowEntity> = repo.getShowDetail(showId)

    fun insertFavoriteMovie(movie: DetailMovieEntity) = viewModelScope.launch {
        repo.insertFavoriteMovie(movie)
    }

    fun insertFavoriteShow(show: DetailShowEntity) = viewModelScope.launch {
        repo.insertFavoriteShow(show)
    }


}