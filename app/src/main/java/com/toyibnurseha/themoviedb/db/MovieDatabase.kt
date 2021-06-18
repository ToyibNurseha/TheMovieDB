package com.toyibnurseha.themoviedb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.utils.Constant.DATABASE_NAME

@Database(entities = [MovieEntity::class, TVShowEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getFavoriteMovie() : FavoriteMovieDAO

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        @JvmStatic
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

}