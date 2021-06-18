package com.toyibnurseha.themoviedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository
import com.toyibnurseha.themoviedb.utils.Resource
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repo: MovieRepository) : ViewModel() {

    fun setMoviesData(id: Int) : LiveData<Resource<MovieEntity>> {
        repo.loadMoviesDetails(id).also {
            return it
        }
    }

    fun setShowData(id: Int) : LiveData<Resource<TVShowEntity>> {
        repo.loadTvShowsDetails(id).also {
            return it
        }
    }

    fun insertFavoriteMovie(movie: MovieEntity) {
        var newValue = movie.addWatchlist
        movie.also { moviesEntity ->
            newValue = newValue == false
            repo.setMoviesWatchlist(moviesEntity, newValue)
        }
    }

    fun insertFavoriteShow(show: TVShowEntity) = viewModelScope.launch {
        var newValue = show.addWatchlist
        show.also { showEntity ->
            newValue = newValue == false
            repo.setTvShowsWatchlist(showEntity, newValue)
        }
    }


}