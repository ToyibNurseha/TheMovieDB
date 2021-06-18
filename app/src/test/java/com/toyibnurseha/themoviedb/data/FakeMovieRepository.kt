package com.toyibnurseha.themoviedb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.toyibnurseha.themoviedb.data.response.movie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.response.show.DetailShowEntity
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.data.source.MovieDataSource
import com.toyibnurseha.themoviedb.data.source.local.MovieLocalDataSource
import com.toyibnurseha.themoviedb.data.source.remote.APIResponse
import com.toyibnurseha.themoviedb.data.source.remote.MovieRemoteDataSource
import com.toyibnurseha.themoviedb.utils.AppExecutors
import com.toyibnurseha.themoviedb.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FakeMovieRepository(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localRemoteDataSource: MovieLocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDataSource {

    override fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieEntity>>(appExecutors) {
            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localRemoteDataSource.getFavoriteMovies(), config).build()
            }

            override fun createCall(): LiveData<APIResponse<List<MovieEntity>>> {
                return remoteDataSource.getPopularMovies()
            }

            override fun saveCallResult(data: List<MovieEntity>) {
                val moviesList = ArrayList<MovieEntity>()

                for (moviesData in data) {
                    moviesData.apply {
                        MovieEntity(
                            id = id,
                            backdropPath = backdropPath,
                            posterPath = posterPath,
                            title = title,
                            voteAverage = voteAverage,
                            releaseDate = releaseDate,
                            overview = overview,
                            video = false
                        ).also {
                            moviesList.add(it)
                        }
                    }
                }
                localRemoteDataSource.insertMovies(moviesList)
            }

        }.asLiveData()
    }

    override fun getPopularShow(): LiveData<Resource<PagedList<TVShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TVShowEntity>, List<TVShowEntity>>(appExecutors) {
            override fun shouldFetch(data: PagedList<TVShowEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDB(): LiveData<PagedList<TVShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(2)
                    .build()

                return LivePagedListBuilder(
                    localRemoteDataSource.getFavoriteShows(),
                    config
                ).build()
            }

            override fun createCall(): LiveData<APIResponse<List<TVShowEntity>>> {
                return remoteDataSource.getPopularShows()
            }

            override fun saveCallResult(data: List<TVShowEntity>) {
                val showsList = ArrayList<TVShowEntity>()

                for (showsData in data) {
                    showsData.apply {
                        TVShowEntity(
                            id = id,
                            backdrop_path = backdrop_path,
                            posterPath = posterPath,
                            name = name,
                            voteAverage = voteAverage,
                            firstAirDate = firstAirDate,
                            overview = overview,
                        ).also {
                            showsList.add(it)
                        }
                    }
                }
                localRemoteDataSource.insertTvShows(showsList)
            }

        }.asLiveData()
    }

    override fun setMoviesWatchlist(movies: MovieEntity, state: Boolean) {
        return appExecutors.diskIO().execute {
            localRemoteDataSource.updateMoviesWatchlist(movies, state)
        }
    }

    override fun setTvShowsWatchlist(tvShows: TVShowEntity, state: Boolean) {
        return appExecutors.diskIO().execute {
            localRemoteDataSource.updateTvShowsWatchlist(tvShows, state)
        }
    }

    override fun getMoviesWatchlist(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localRemoteDataSource.getWatchListMovie(), config).build()
    }

    override fun getTvShowsWatchlist(): LiveData<PagedList<TVShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localRemoteDataSource.getWatchListShows(), config).build()
    }

    override fun loadMoviesDetails(moviesID: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, DetailMovieEntity>(appExecutors) {
            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data == null
            }

            override fun loadFromDB(): LiveData<MovieEntity> {
                return localRemoteDataSource.getMoviesById(moviesID)
            }

            override fun createCall(): LiveData<APIResponse<DetailMovieEntity>> {
                return remoteDataSource.getMovieDetail(moviesID)
            }

            override fun saveCallResult(data: DetailMovieEntity) {
                data.apply {
                    val moviesEntity = MovieEntity(
                        id = id,
                        popularity = popularity,
                        releaseDate = releaseDate,
                        voteCount = voteCount,
                        backdropPath = backdropPath,
                        posterPath = posterPath,
                        title = title,
                        voteAverage = voteAverage,
                        overview = overview,
                        addWatchlist = false
                    )

                    localRemoteDataSource.updateMoviesWatchlist(moviesEntity, false)
                }
            }
        }.asLiveData()
    }

    override fun loadTvShowsDetails(tvShowsID: Int): LiveData<Resource<TVShowEntity>> {
        return object : NetworkBoundResource<TVShowEntity, DetailShowEntity>(appExecutors) {
            override fun shouldFetch(data: TVShowEntity?): Boolean {
                return data == null
            }

            override fun loadFromDB(): LiveData<TVShowEntity> {
                return localRemoteDataSource.getTvShowsById(tvShowsID)
            }

            override fun createCall(): LiveData<APIResponse<DetailShowEntity>> {
                return remoteDataSource.getShowDetail(tvShowsID)
            }

            override fun saveCallResult(data: DetailShowEntity) {
                data.apply {
                    val showEntity = TVShowEntity(
                        id = id,
                        popularity = popularity,
                        firstAirDate = firstAirDate,
                        voteCount = voteCount,
                        backdrop_path = backdropPath,
                        posterPath = posterPath,
                        name = name,
                        voteAverage = voteAverage,
                        overview = overview,
                        addWatchlist = false
                    )

                    localRemoteDataSource.updateTvShowsWatchlist(showEntity, false)
                }
            }
        }.asLiveData()
    }

}