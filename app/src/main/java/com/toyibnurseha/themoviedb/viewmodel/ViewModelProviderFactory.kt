package com.toyibnurseha.themoviedb.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toyibnurseha.themoviedb.di.Injection
import com.toyibnurseha.themoviedb.repository.MovieRepository
import com.toyibnurseha.themoviedb.ui.detail.MovieDetailViewModel
import com.toyibnurseha.themoviedb.ui.favorite.FavoriteViewModel
import com.toyibnurseha.themoviedb.ui.movie.MovieViewModel
import com.toyibnurseha.themoviedb.ui.show.ShowViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: ViewModelProviderFactory? = null

        fun getInstance(context: Context): ViewModelProviderFactory =
            instance ?: synchronized(this) {
                ViewModelProviderFactory(Injection.provideRepository(context)).apply { instance = this }
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(repo) as T
            }

            modelClass.isAssignableFrom(ShowViewModel::class.java) -> {
                ShowViewModel(repo) as T
            }

            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(repo) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(repo) as T
            }

            else -> throw Throwable("unknown viewModel class" + modelClass.name)
        }
    }
}