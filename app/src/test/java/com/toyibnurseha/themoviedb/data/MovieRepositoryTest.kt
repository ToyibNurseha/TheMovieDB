package com.toyibnurseha.themoviedb.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.data.source.remote.MovieRemoteDataSource
import com.toyibnurseha.themoviedb.utils.DummyData
import com.toyibnurseha.themoviedb.utils.LiveDataTestUtil
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(MovieRemoteDataSource::class.java)
    private val movieRepo = FakeMovieRepository(remote)

    private val movieList = DummyData.generateMovieData()
    private val movieId = movieList.first().id
    private var dumMovie = DetailMovieEntity(
        backdropPath = movieList.first().backdropPath,
        id = movieList.first().id,
        originalTitle = movieList.first().originalTitle,
        voteCount = movieList.first().voteCount,
        voteAverage = movieList.first().voteAverage,
        posterPath = movieList.first().posterPath,
        releaseDate = movieList.first().releaseDate
    )

    private val showList = DummyData.generateShowsData()
    private val showId = showList.first().id
    private var dumShow = DetailShowEntity(
        backdropPath = showList.first().backdrop_path,
        id = showList.first().id,
        name = showList.first().name,
        voteCount = showList.first().voteCount,
        voteAverage = showList.first().voteAverage,
        posterPath = showList.first().posterPath,
        firstAirDate = showList.first().firstAirDate
    )

    @Test
    fun getMovies(){
        val coroutine = CoroutineScope(Dispatchers.IO)
        coroutine.launch {
            doAnswer { invocation ->
                (invocation.arguments[0] as MovieRemoteDataSource.LoadPopularMovieCallback).responsePopularMovie(movieList)
                null
            }.`when`(remote).getPopularMovies(any())
            val movieEntity = LiveDataTestUtil.getValue(movieRepo.getPopularMovies())
            verify(remote).getPopularMovies(any())
            assertNotNull(movieEntity)
            assertEquals(movieList.size.toLong(), movieList.size.toLong())
        }
    }

    @Test
    fun getMovieDetail(){
        CoroutineScope(Dispatchers.IO).launch {
            movieId?.let {
                doAnswer { invocation ->
                    (invocation.arguments[1] as MovieRemoteDataSource.LoadMovieDetailCallback).responseMovieDetail(dumMovie)
                    null
                }.`when`(remote).getMovieDetail(eq(it), any())

                val movieEntity = LiveDataTestUtil.getValue(movieRepo.getMovieDetail(it))
                verify(remote).getMovieDetail(it, any())
                assertNotNull(movieEntity)
                assertEquals(movieEntity.title, movieEntity.title)
            }
        }
    }

    @Test
    fun getPopularShow(){
        val coroutine = CoroutineScope(Dispatchers.IO)
        coroutine.launch {
            doAnswer { invocation ->
                (invocation.arguments[0] as MovieRemoteDataSource.LoadShowCallback).responsePopularShow(showList)
                null
            }.`when`(remote).getPopularShows(any())
            val showEntity = LiveDataTestUtil.getValue(movieRepo.getPopularShow())
            verify(remote).getPopularShows(any())
            assertNotNull(showEntity)
            assertEquals(showList.size.toLong(), showList.size.toLong())
        }
    }

    @Test
    fun getShowDetail(){
        CoroutineScope(Dispatchers.IO).launch {
            showId?.let {
                doAnswer { invocation ->
                    (invocation.arguments[1] as MovieRemoteDataSource.LoadShowDetailCallback).responseDetailShow(dumShow)
                    null
                }.`when`(remote).getShowDetail(eq(it), any())

                val showEntity = LiveDataTestUtil.getValue(movieRepo.getShowDetail(it))
                verify(remote).getShowDetail(it, any())
                assertNotNull(showEntity)
                assertEquals(showEntity.name, showEntity.name)
            }
        }
    }

}