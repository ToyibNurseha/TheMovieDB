package com.toyibnurseha.themoviedb.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.toyibnurseha.themoviedb.data.response.movie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.DetailShowEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.data.source.local.MovieLocalDataSource
import com.toyibnurseha.themoviedb.data.source.remote.MovieRemoteDataSource
import com.toyibnurseha.themoviedb.utils.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(MovieRemoteDataSource::class.java)
    private val local = Mockito.mock(MovieLocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val movieRepo = FakeMovieRepository(remote, local, appExecutors)

    private val movieList = DummyData.generateMovieData()
    private val movieId = movieList.first().id
    private val movieDummy = movieList.first()

    private val showList = DummyData.generateShowsData()
    private val showId = showList.first().id
    private val showDummy = showList.first()

    @Test
    fun getMovies(){
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        movieRepo.getPopularMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DummyData.generateMovieData()))
        verify(local).getFavoriteMovies()
        Assert.assertNotNull(movieEntities.data)
        assertEquals(movieList.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieDetail(){
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = DummyData.generateMovieData().first()
        Mockito.`when`(local.getMoviesById(movieId!!)).thenReturn(dummyEntity)

        val movieEntity = LiveDataTestUtil.getValue(movieRepo.loadMoviesDetails(movieId))
        verify(local).getMoviesById(movieId)
        Assert.assertNotNull(movieEntity.data)
        assertEquals(movieDummy.title, movieEntity.data!!.title)
    }

    @Test
    fun getPopularShow(){
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVShowEntity>
        Mockito.`when`(local.getFavoriteShows()).thenReturn(dataSourceFactory)
        movieRepo.getPopularShow()

        val showEntity = Resource.success(PagedListUtil.mockPagedList(DummyData.generateShowsData()))
        verify(local).getFavoriteShows()
        Assert.assertNotNull(showEntity.data)
        assertEquals(showList.size.toLong(), showEntity.data?.size?.toLong())
    }

    @Test
    fun getShowDetail(){
        val dummyEntity = MutableLiveData<TVShowEntity>()
        dummyEntity.value = DummyData.generateShowsData().first()
        Mockito.`when`(local.getTvShowsById(showId!!)).thenReturn(dummyEntity)

        val movieEntity = LiveDataTestUtil.getValue(movieRepo.loadTvShowsDetails(showId))
        verify(local).getTvShowsById(showId)
        Assert.assertNotNull(movieEntity.data)
        assertEquals(showDummy.name, movieEntity.data!!.name)
    }

}