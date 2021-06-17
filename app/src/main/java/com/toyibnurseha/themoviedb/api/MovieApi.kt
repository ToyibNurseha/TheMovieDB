package com.toyibnurseha.themoviedb.api

import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.data.movie.MovieResponse
import com.toyibnurseha.themoviedb.data.show.TVShowResponse
import com.toyibnurseha.themoviedb.utils.Constant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("3/movie/popular?api_key=${Constant.API_KEY}")
    fun getPopularMovies() : Call<MovieResponse>

    @GET("3/tv/popular?api_key=${Constant.API_KEY}")
    fun getPopularShow() : Call<TVShowResponse>

    @GET("3/movie/{id}?api_key=${Constant.API_KEY}")
    fun getDetailMovie(
        @Path(value = "id", encoded = true)
        id: Int
    ) : Call<DetailMovieEntity>

    @GET("3/tv/{id}?api_key=${Constant.API_KEY}")
    fun getDetailShow(
        @Path(value = "id", encoded = true)
        id: Int
    ) : Call<DetailShowEntity>
}