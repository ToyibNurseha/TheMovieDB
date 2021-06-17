package com.toyibnurseha.themoviedb.ui.show

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.show.TVShowEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository
import com.toyibnurseha.themoviedb.utils.DummyData
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShowViewModelTest {

    private lateinit var viewModel: ShowViewModel
    private val dummyShows = DummyData.generateShowsData()

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<TVShowEntity>>

    @Mock
    private lateinit var movieRepo: MovieRepository

    @Before
    fun setup() {
        viewModel = ShowViewModel(movieRepo)
    }

    @Test
    fun getShowsData() {
        val shows = MutableLiveData<List<TVShowEntity>>()
        shows.value = dummyShows

        Mockito.`when`(movieRepo.getPopularShow()).thenReturn(shows)

        val showList = viewModel.getPopularShow().value
        verify(movieRepo).getPopularShow()
        Assert.assertNotNull(showList)
        assertEquals(5, showList?.size)

        viewModel.getPopularShow().observeForever(observer)
        verify(observer).onChanged(dummyShows)
    }

    @Test
    fun getEmptyShow() {
        val shows = viewModel.getEmptyShow()
        Assert.assertNotNull(shows)
        assertEquals(0, shows.size)
    }

}