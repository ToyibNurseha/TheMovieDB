package com.toyibnurseha.themoviedb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.show.TVShowEntity
import com.toyibnurseha.themoviedb.data.source.MovieDataSource
import com.toyibnurseha.themoviedb.data.source.local.MovieLocalDataSource
import com.toyibnurseha.themoviedb.data.source.remote.MovieRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieRepository private constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localRemoteDataSource: MovieLocalDataSource
) :
    MovieDataSource {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteData: MovieRemoteDataSource,
            localData: MovieLocalDataSource
        ): MovieRepository =
            instance ?: synchronized(this) {
                MovieRepository(remoteData, localData).apply { instance = this }
            }
    }

    override fun getPopularMovies(): LiveData<List<MovieEntity>> {
        val movieResponse = MutableLiveData<List<MovieEntity>>()
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getPopularMovies(object :
                MovieRemoteDataSource.LoadPopularMovieCallback {
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
        CoroutineScope(Dispatchers.IO).launch {
            movieId?.let {
                remoteDataSource.getMovieDetail(
                    movieId,
                    object : MovieRemoteDataSource.LoadMovieDetailCallback {
                        override fun responseMovieDetail(response: DetailMovieEntity) {
                            val movie = DetailMovieEntity(
                                id = response.id,
                                backdropPath = response.backdropPath,
                                budget = response.budget,
                                originalTitle = response.originalTitle,
                                originalLanguage = response.originalLanguage,
                                status = response.status,
                                tagline = response.tagline,
                                video = response.video,
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
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getPopularShows(object : MovieRemoteDataSource.LoadShowCallback {
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
        CoroutineScope(Dispatchers.IO).launch {
            remoteDataSource.getShowDetail(
                showId,
                object : MovieRemoteDataSource.LoadShowDetailCallback {
                    override fun responseDetailShow(response: DetailShowEntity) {
                        val tvShow = DetailShowEntity(
                            id = response.id,
                            backdropPath = response.backdropPath,
                            originalLanguage = response.originalLanguage,
                            status = response.status,
                            tagline = response.tagline,
                            video = response.video,
                            name = response.name,
                            firstAirDate = response.firstAirDate,
                            popularity = response.popularity,
                            overview = response.overview,
                            voteAverage = response.voteAverage,
                            voteCount = response.voteCount,
                            posterPath = response.posterPath,
                            lastAirDate = response.lastAirDate
                        )
                        detailShow.postValue(tvShow)
                    }

                })
        }
        return detailShow
    }

    override suspend fun insertFavoriteMovie(movie: DetailMovieEntity) {
        localRemoteDataSource.db.getFavoriteMovie().insertMovie(movie)
    }

    override suspend fun insertFavoriteShow(show: DetailShowEntity) {
        localRemoteDataSource.db.getFavoriteMovie().insertShow(show)
    }

    override fun getLocalFavoriteShow(): LiveData<List<DetailShowEntity>> {
        val movieResult = MutableLiveData<List<DetailShowEntity>>()
        localRemoteDataSource.getFavoriteShow(object :
            MovieLocalDataSource.LoadFavoriteShowCallback {
            override fun responsePopularShow(response: List<DetailShowEntity>) {
                val movies = ArrayList<DetailShowEntity>()
                for (movie in response) {
                    val movieData = DetailShowEntity(
                        backdropPath = movie.backdropPath,
                        id = movie.id,
                        name = movie.name,
                        firstAirDate = movie.firstAirDate,
                        voteCount = movie.voteCount,
                        voteAverage = movie.voteAverage,
                        posterPath = movie.posterPath,
                        originalName = movie.originalName,
                        popularity = movie.popularity,
                    )
                    movies.add(movieData)
                }
                movieResult.postValue(movies)
            }
        })
        return movieResult
    }

    override fun getLocalFavoriteDetailShow(): LiveData<List<DetailShowEntity>> {
        TODO("Not yet implemented")
    }

    override fun getLocalFavoriteMovie(): LiveData<List<DetailMovieEntity>> {
        val movieResult = MutableLiveData<List<DetailMovieEntity>>()
        localRemoteDataSource.getFavoriteMovies(object :
            MovieLocalDataSource.LoadFavoriteMovieCallback {
            override fun responseFavoriteMovie(response: List<DetailMovieEntity>) {
                val movies = ArrayList<DetailMovieEntity>()
                for (movie in response) {
                    val movieData = DetailMovieEntity(
                        backdropPath = movie.backdropPath,
                        id = movie.id,
                        originalTitle = movie.originalTitle,
                        releaseDate = movie.releaseDate,
                        voteCount = movie.voteCount,
                        voteAverage = movie.voteAverage,
                        posterPath = movie.posterPath,
                        title = movie.title,
                        popularity = movie.popularity
                    )
                    movies.add(movieData)
                }
                movieResult.postValue(movies)
            }
        })
        return movieResult
    }

    override fun getFavoriteDetailMovie(): LiveData<List<DetailMovieEntity>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteShow(): LiveData<List<TVShowEntity>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteDetailShow(): LiveData<List<DetailShowEntity>> {
        TODO("Not yet implemented")
    }
}