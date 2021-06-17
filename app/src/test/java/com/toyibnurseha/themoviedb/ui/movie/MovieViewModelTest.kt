package com.toyibnurseha.themoviedb.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository
import com.toyibnurseha.themoviedb.utils.DummyData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel
    private val dummyMovies = DummyData.generateMovieData()

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @Mock
    private lateinit var movieRepo: MovieRepository

    @Before
    fun setup() {
        viewModel = MovieViewModel(movieRepo)
    }

    @Test
    fun getMoviesData() {
        val movies = MutableLiveData<List<MovieEntity>>()
        movies.value = dummyMovies

        `when`(movieRepo.getPopularMovies()).thenReturn(movies)

        val movieList = viewModel.getPopularMovies().value
        verify(movieRepo).getPopularMovies()
        assertNotNull(movieList)
        assertEquals(5, movieList?.size)

        viewModel.getPopularMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getMoviesEmpty() {
        val movies = viewModel.getEmptyMovie()
        assertNotNull(movies)
        assertEquals(0, movies.size)
    }

}