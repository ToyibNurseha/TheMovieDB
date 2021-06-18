package com.toyibnurseha.themoviedb.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository
import com.toyibnurseha.themoviedb.utils.DummyData
import com.toyibnurseha.themoviedb.utils.Resource
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
    private val dummyShow = DummyData.generateShowsData().first()
    private val movieId = dummyMovie.id
    private val showId = dummyShow.id

    @get:Rule
    var instantTaskRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepo: MovieRepository

    @Before
    fun setupTest() {
        viewModel = MovieDetailViewModel(movieRepo)
        viewModel.setMoviesData(dummyMovie.id!!)
    }

    @Test
    fun testGetMovieDetail() {
        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(DummyData.generateMovieData().first())

        `when`(movieRepo.loadMoviesDetails(movieId!!)).thenReturn(expected)

        viewModel.setMoviesData(movieId)

        val expectedValue = expected.value
        val actualValue = viewModel.setMoviesData(movieId).value

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun testGetShowDetail() {
        val expected = MutableLiveData<Resource<TVShowEntity>>()
        expected.value = Resource.success(DummyData.generateShowsData().first())

        `when`(movieRepo.loadTvShowsDetails(showId!!)).thenReturn(expected)

        viewModel.setShowData(showId)

        val expectedValue = expected.value
        val actualValue = viewModel.setShowData(showId).value

        assertEquals(expectedValue, actualValue)
    }

}