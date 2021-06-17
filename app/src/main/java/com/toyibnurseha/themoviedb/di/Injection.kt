package com.toyibnurseha.themoviedb.di

import android.content.Context
import com.toyibnurseha.themoviedb.data.source.local.MovieLocalDataSource
import com.toyibnurseha.themoviedb.data.source.remote.MovieRemoteDataSource
import com.toyibnurseha.themoviedb.db.MovieDatabase
import com.toyibnurseha.themoviedb.repository.MovieRepository

object Injection {

    fun provideRepository(context: Context) : MovieRepository {
        val remoteRepository = MovieRemoteDataSource.getInstance()
        val movieDatabase = MovieDatabase(context)
        val localRepository = MovieLocalDataSource.getInstance(movieDatabase)
        return MovieRepository.getInstance(remoteRepository, localRepository)
    }

}