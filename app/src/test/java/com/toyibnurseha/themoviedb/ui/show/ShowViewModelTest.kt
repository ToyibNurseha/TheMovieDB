package com.toyibnurseha.themoviedb.ui.show

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.nhaarman.mockitokotlin2.verify
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.repository.MovieRepository
import com.toyibnurseha.themoviedb.utils.DummyData
import com.toyibnurseha.themoviedb.utils.Resource
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class ShowViewModelTest {

    private lateinit var viewModel: ShowViewModel

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TVShowEntity>>>

    @Mock
    private lateinit var movieRepo: MovieRepository

    @Before
    fun setup() {
        viewModel = ShowViewModel(movieRepo)
    }

    @Test
    fun getShowsData() {
        val show = PagedTestDataSources.snapshot(DummyData.generateShowsData())
        val expected = MutableLiveData<Resource<PagedList<TVShowEntity>>>()
        expected.value = Resource.success(show)

        Mockito.`when`(movieRepo.getPopularShow()).thenReturn(expected)

        viewModel.getPopularShow().observeForever(observer)
        Mockito.verify(observer).onChanged(expected.value)
        verify(movieRepo).getPopularShow()
        val expectedValue = expected.value
        val actualValue = viewModel.getPopularShow().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.data, actualValue?.data)
        assertEquals(expectedValue?.data?.size, actualValue?.data?.size)
    }

    @Test
    fun getEmptyShow() {
        val shows = viewModel.getEmptyShow()
        Assert.assertNotNull(shows)
        assertEquals(0, shows.size)
    }

    class PagedTestDataSources private constructor(private val items: List<TVShowEntity>) :
        PositionalDataSource<TVShowEntity>() {
        companion object {
            fun snapshot(items: List<TVShowEntity> = listOf()): PagedList<TVShowEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<TVShowEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<TVShowEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}