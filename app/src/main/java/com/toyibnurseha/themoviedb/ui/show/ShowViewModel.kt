package com.toyibnurseha.themoviedb.ui.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository
import com.toyibnurseha.themoviedb.utils.Resource

class ShowViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getPopularShow() : LiveData<Resource<PagedList<TVShowEntity>>> = repo.getPopularShow()

    fun getEmptyShow() = arrayListOf<MovieEntity>()
}