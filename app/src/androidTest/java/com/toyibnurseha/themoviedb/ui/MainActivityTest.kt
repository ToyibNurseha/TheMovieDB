package com.toyibnurseha.themoviedb.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.toyibnurseha.themoviedb.R
import com.toyibnurseha.themoviedb.utils.DummyData
import com.toyibnurseha.themoviedb.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    private var moviesData = DummyData.generateMovieData()
    private var showData = DummyData.generateShowsData()

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rvMovies)).check(matches(isDisplayed()))
        onView(withId(R.id.rvMovies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(moviesData.size))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rvMovies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            click()
        ))
        onView(withId(R.id.tvTitleDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleDetail)).check(matches(withText(moviesData.first().title)))

        onView(withId(R.id.tvDescriptionDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDescriptionDetail)).check(matches(withText(moviesData.first().overview)))

        onView(withId(R.id.tvRateDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRateDetail)).check(matches(withText(moviesData.first().voteAverage.toString())))

        onView(withId(R.id.tvReleaseDateDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDateDetail)).check(matches(withText(moviesData.first().releaseDate)))
    }

    @Test
    fun loadShows() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rvShow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(showData.size))
    }

    @Test
    fun loadDetailShow() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            click()
        ))
        onView(withId(R.id.tvTitleDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleDetail)).check(matches(withText(showData.first().name)))

        onView(withId(R.id.tvDescriptionDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDescriptionDetail)).check(matches(withText(showData.first().overview)))

        onView(withId(R.id.tvRateDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRateDetail)).check(matches(withText(showData.first().voteAverage.toString())))

        onView(withId(R.id.tvReleaseDateDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDateDetail)).check(matches(withText(showData.first().firstAirDate)))
    }
}