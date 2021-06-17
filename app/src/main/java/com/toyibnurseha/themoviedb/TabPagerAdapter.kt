package com.toyibnurseha.themoviedb

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.toyibnurseha.themoviedb.ui.favorite.FavoriteFragment
import com.toyibnurseha.themoviedb.ui.movie.MovieFragment
import com.toyibnurseha.themoviedb.ui.show.TvShowFragment

class TabPagerAdapter(fragmentManager: FragmentActivity) : FragmentStateAdapter(fragmentManager) {

    companion object {
        const val MOVIE_FRAGMENT = 0
        const val SHOW_FRAGMENT = 1
        const val FAVORITE_FRAGMENT = 2
    }

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            MOVIE_FRAGMENT -> MovieFragment()
            SHOW_FRAGMENT -> TvShowFragment()
            FAVORITE_FRAGMENT -> FavoriteFragment()
            else -> MovieFragment()
        }
    }

}