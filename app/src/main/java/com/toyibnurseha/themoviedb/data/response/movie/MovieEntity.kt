package com.toyibnurseha.themoviedb.data.response.movie

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.toyibnurseha.themoviedb.utils.Constant.MOVIE_TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Entity(tableName = MOVIE_TABLE_NAME)
@Parcelize
data class MovieEntity(
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    @ColumnInfo(name = "add_watchlist")
    var addWatchlist: Boolean = false
) : Parcelable