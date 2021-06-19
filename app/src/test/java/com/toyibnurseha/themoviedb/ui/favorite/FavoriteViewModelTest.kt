package com.toyibnurseha.themoviedb.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository
import com.toyibnurseha.themoviedb.ui.movie.MovieViewModelTest
import com.toyibnurseha.themoviedb.ui.show.ShowViewModelTest
import com.toyibnurseha.themoviedb.utils.DummyData
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var observerTvShow: Observer<PagedList<TVShowEntity>>

    @Mock
    private lateinit var movieRepo: MovieRepository

    @Before
    fun setup() {
        viewModel = FavoriteViewModel(movieRepo)
    }

    @Test
    fun getWatchListMoviesData() {
        val movies = MovieViewModelTest.PagedTestDataSources.snapshot(DummyData.generateMovieData())
        val expected = MutableLiveData<PagedList<MovieEntity>>()
        expected.value = movies

        Mockito.`when`(movieRepo.getMoviesWatchlist()).thenReturn(expected)

        viewModel.getMoviesWatchlist().observeForever(observer)
        Mockito.verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getMoviesWatchlist().value
        Assert.assertEquals(expectedValue, actualValue)
        Assert.assertEquals(expectedValue?.size, actualValue?.size)
    }

    @Test
    fun getWatchListShowsData() {
        val tvShow = ShowViewModelTest.PagedTestDataSources.snapshot(DummyData.generateShowsData())
        val expected = MutableLiveData<PagedList<TVShowEntity>>()
        expected.value = tvShow

        Mockito.`when`(movieRepo.getTvShowsWatchlist()).thenReturn(expected)

        viewModel.getTvShowsWatchlist().observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getTvShowsWatchlist().value
        Assert.assertEquals(expectedValue, actualValue)
        Assert.assertEquals(expectedValue?.size, actualValue?.size)
    }

    @Test
    fun getMoviesEmptyWatchList() {
        val moviesWatchList = viewModel.getEmptyMovieWatchList()
        Assert.assertNotNull(moviesWatchList)
        Assert.assertEquals(0, moviesWatchList.size)
    }

    @Test
    fun getShowEmptyWatchList() {
        val showWatchList = viewModel.getEmptyShowWatchList()
        Assert.assertNotNull(showWatchList)
        Assert.assertEquals(0, showWatchList.size)
    }

}