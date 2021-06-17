package com.toyibnurseha.themoviedb.data.show

data class TVShowResponse(
    val page: Int,
    val results: MutableList<TVShowEntity>,
    val total_pages: Int,
    val total_results: Int
)