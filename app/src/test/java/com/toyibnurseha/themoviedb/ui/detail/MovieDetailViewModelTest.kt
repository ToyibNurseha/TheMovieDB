package com.toyibnurseha.themoviedb.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository
import com.toyibnurseha.themoviedb.utils.DummyData
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private var dummyMovie = DummyData.generateMovieData().first()
    private var dumMovie = DetailMovieEntity(
        backdropPath = dummyMovie.backdropPath,
        id = dummyMovie.id,
        originalTitle = dummyMovie.originalTitle,
        voteCount = dummyMovie.voteCount,
        voteAverage = dummyMovie.voteAverage,
        posterPath = dummyMovie.posterPath,
        releaseDate = dummyMovie.releaseDate
    )
    private val dummyShow = DummyData.generateShowsData().first()
    private var dumShow = DetailShowEntity(
        backdropPath = dummyShow.backdrop_path,
        id = dummyShow.id,
        name = dummyShow.name,
        voteCount = dummyShow.voteCount,
        voteAverage = dummyShow.voteAverage,
        posterPath = dummyShow.posterPath,
        firstAirDate = dummyShow.firstAirDate
    )
    private val movieId = dummyMovie.id
    private val showId = dummyShow.id

    @get:Rule
    var instantTaskRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepo: MovieRepository

    @Mock
    private lateinit var movieObserver: Observer<DetailMovieEntity>
    @Mock
    private lateinit var showObserver: Observer<DetailShowEntity>

    @Before
    fun setupTest() {
        viewModel = MovieDetailViewModel(movieRepo)
    }

    @Test
    fun testGetMovieDetail() {
        val movieData = MutableLiveData<DetailMovieEntity>()
        movieData.value = dumMovie

        `when`(movieId?.let { movieRepo.getMovieDetail(it) }).thenReturn(movieData)
        val movieEntity = movieId?.let { viewModel.getDetailMovie(it).value }
        if (movieId != null) {
            verify(movieRepo).getMovieDetail(movieId)
        }
        Assert.assertNotNull(movieEntity)
        assertEquals(dumMovie.id, movieEntity?.id)
        assertEquals(dumMovie.overview, movieEntity?.overview)
        assertEquals(dumMovie.releaseDate, movieEntity?.releaseDate)
        assertEquals(dumMovie.voteAverage, movieEntity?.voteAverage)
        assertEquals(dumMovie.backdropPath, movieEntity?.backdropPath)
        assertEquals(dumMovie.title, movieEntity?.title)
        assertEquals(dumMovie.voteCount, movieEntity?.voteCount)

        if (movieId != null) {
            viewModel.getDetailMovie(movieId).observeForever(movieObserver)
            verify(movieObserver).onChanged(dumMovie)
        }
    }

    @Test
    fun testGetShowDetail() {
        val showData = MutableLiveData<DetailShowEntity>()
        showData.value = dumShow

        `when`(showId?.let { movieRepo.getShowDetail(it) }).thenReturn(showData)
        val showEntity = showId?.let { viewModel.getDetailShow(it).value }
        if (showId != null) {
            verify(movieRepo).getShowDetail(showId)
        }
        Assert.assertNotNull(showEntity)
        assertEquals(dumShow.id, showEntity?.id)
        assertEquals(dumShow.overview, showEntity?.overview)
        assertEquals(dumShow.firstAirDate, showEntity?.firstAirDate)
        assertEquals(dumShow.voteAverage, showEntity?.voteAverage)
        assertEquals(dumShow.backdropPath, showEntity?.backdropPath)
        assertEquals(dumShow.name, showEntity?.name)
        assertEquals(dumShow.voteCount, showEntity?.voteCount)

        if (showId != null) {
            viewModel.getDetailShow(showId).observeForever(showObserver)
            verify(showObserver).onChanged(dumShow)
        }
    }

}