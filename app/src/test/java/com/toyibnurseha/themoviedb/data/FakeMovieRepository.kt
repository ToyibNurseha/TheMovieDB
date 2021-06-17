package com.toyibnurseha.themoviedb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.show.TVShowEntity
import com.toyibnurseha.themoviedb.data.source.MovieDataSource
import com.toyibnurseha.themoviedb.data.source.remote.MovieRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FakeMovieRepository(private val remoteDataSource: MovieRemoteDataSource) : MovieDataSource {
    override fun getPopularMovies(): LiveData<List<MovieEntity>> {
        val movieResponse = MutableLiveData<List<MovieEntity>>()
        val coroutine = CoroutineScope(Dispatchers.IO)
        coroutine.launch {
            remoteDataSource.getPopularMovies(object: MovieRemoteDataSource.LoadPopularMovieCallback {
                override fun responsePopularMovie(response: List<MovieEntity>) {
                    val movies = ArrayList<MovieEntity>()
                    for (counter in response) {
                        val movie = MovieEntity(
                            backdropPath = counter.backdropPath,
                            id = counter.id,
                            originalTitle = counter.originalTitle,
                            releaseDate = counter.releaseDate,
                            voteCount = counter.voteCount,
                            voteAverage = counter.voteAverage,
                            posterPath = counter.posterPath,
                            title = counter.title,
                            popularity = counter.popularity
                        )
                        movies.add(movie)
                    }
                    movieResponse.postValue(movies)
                }
            })
        }
        return movieResponse
    }

    override fun getMovieDetail(movieId: Int?): LiveData<DetailMovieEntity> {
        val detailMovie = MutableLiveData<DetailMovieEntity>()
        val coroutine = CoroutineScope(Dispatchers.IO)
        coroutine.launch {
            movieId?.let {
                remoteDataSource.getMovieDetail(movieId, object : MovieRemoteDataSource.LoadMovieDetailCallback {
                    override fun responseMovieDetail(response: DetailMovieEntity) {
                        val movie = DetailMovieEntity(
                            backdropPath = response.backdropPath,
                            title = response.title,
                            releaseDate = response.releaseDate,
                            popularity = response.popularity,
                            overview = response.overview,
                            voteAverage = response.voteAverage,
                            voteCount = response.voteCount,
                            posterPath = response.posterPath
                        )
                        detailMovie.postValue(movie)
                    }
                })
            }
        }
        return detailMovie
    }

    override fun getPopularShow(): LiveData<List<TVShowEntity>> {
        val showsResponse = MutableLiveData<List<TVShowEntity>>()
        val coroutine = CoroutineScope(Dispatchers.IO)
        coroutine.launch {
            remoteDataSource.getPopularShows(object: MovieRemoteDataSource.LoadShowCallback {
                override fun responsePopularShow(response: List<TVShowEntity>) {
                    val tvShows = ArrayList<TVShowEntity>()
                    for (counter in response) {
                        val show = TVShowEntity(
                            backdrop_path = counter.backdrop_path,
                            id = counter.id,
                            originalName = counter.originalName,
                            firstAirDate = counter.firstAirDate,
                            voteCount = counter.voteCount,
                            voteAverage = counter.voteAverage,
                            posterPath = counter.posterPath,
                            name = counter.name,
                            popularity = counter.popularity
                        )
                        tvShows.add(show)
                    }
                    showsResponse.postValue(tvShows)
                }
            })
        }
        return showsResponse
    }

    override fun getShowDetail(showId: Int): LiveData<DetailShowEntity> {
        val detailShow = MutableLiveData<DetailShowEntity>()
        val coroutine = CoroutineScope(Dispatchers.IO)
        coroutine.launch {
            remoteDataSource.getShowDetail(showId, object : MovieRemoteDataSource.LoadShowDetailCallback {
                override fun responseDetailShow(response: DetailShowEntity) {
                    val tvShow = DetailShowEntity(
                        backdropPath = response.backdropPath,
                        name = response.name,
                        firstAirDate = response.firstAirDate,
                        popularity = response.popularity,
                        overview = response.overview,
                        voteAverage = response.voteAverage,
                        voteCount = response.voteCount,
                        posterPath = response.posterPath
                    )
                    detailShow.postValue(tvShow)
                }

            })
        }
        return detailShow
    }
}