package com.toyibnurseha.themoviedb.api

import com.toyibnurseha.themoviedb.data.response.movie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.response.movie.MovieResponse
import com.toyibnurseha.themoviedb.data.response.show.DetailShowEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowResponse
import com.toyibnurseha.themoviedb.utils.Constant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("3/movie/popular?api_key=${Constant.API_KEY}")
    fun getPopularMovies(
        @Query("page") page: Int,
    ) : Call<MovieResponse>

    @GET("3/tv/popular?api_key=${Constant.API_KEY}")
    fun getPopularShow(
        @Query("page") page: Int = 0
    ) : Call<TVShowResponse>

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