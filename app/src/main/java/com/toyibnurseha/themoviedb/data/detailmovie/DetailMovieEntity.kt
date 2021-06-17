package com.toyibnurseha.themoviedb.data.detailmovie

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.toyibnurseha.themoviedb.utils.Constant.MOVIE_TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Entity(tableName = MOVIE_TABLE_NAME)
@Parcelize
data class DetailMovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = 0,
    @SerializedName("backdrop_path")
    val backdropPath: String? = "",
    val budget: Int? = 0,
    @SerializedName("original_language")
    val originalLanguage: String? = "",
    @SerializedName("original_title")
    val originalTitle: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    @SerializedName("poster_path")
    val posterPath: String? = "",
    @SerializedName("release_date")
    val releaseDate: String? = "",
    val status: String? = "",
    val tagline: String? = "",
    val title: String? = "",
    val video: Boolean = false,
    @SerializedName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int? = 0
) : Parcelable