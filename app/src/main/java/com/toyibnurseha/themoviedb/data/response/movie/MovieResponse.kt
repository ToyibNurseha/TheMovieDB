package com.toyibnurseha.themoviedb.data.response.movie

data class MovieResponse(
    val page: Int,
    val results: MutableList<MovieEntity>,
    val total_pages: Int,
    val total_results: Int
)