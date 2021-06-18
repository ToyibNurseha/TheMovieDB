package com.toyibnurseha.themoviedb.di

import android.content.Context
import com.toyibnurseha.themoviedb.data.source.local.MovieLocalDataSource
import com.toyibnurseha.themoviedb.data.source.remote.MovieRemoteDataSource
import com.toyibnurseha.themoviedb.db.MovieDatabase
import com.toyibnurseha.themoviedb.repository.MovieRepository
import com.toyibnurseha.themoviedb.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context) : MovieRepository {
        val movieDatabase = MovieDatabase(context)
        val remoteRepository = MovieRemoteDataSource.getInstance()
        val localRepository = MovieLocalDataSource.getInstance(movieDatabase.getFavoriteMovie())
        val appExecutors = AppExecutors()
        return MovieRepository.getInstance(remoteRepository, localRepository, appExecutors)
    }

}