package com.toyibnurseha.themoviedb.data.detailshow

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.toyibnurseha.themoviedb.utils.Constant.SHOW_TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Entity(tableName = SHOW_TABLE_NAME)
@Parcelize
data class DetailShowEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = 0,
    @SerializedName("backdrop_path")
    val backdropPath: String? = "",
    @SerializedName("first_air_date")
    val firstAirDate: String? = "",
    @SerializedName("last_air_date")
    val lastAirDate: String? = "",
    val name: String? = "",
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int? = 0,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int? = 0,
    @SerializedName("original_language")
    val originalLanguage: String? = "",
    @SerializedName("original_name")
    val originalName: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    @SerializedName("poster_path")
    val posterPath: String? = "",
    val status: String? = "",
    val tagline: String? = "",
    val type: String? = "",
    val video: Boolean = false,
    @SerializedName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int? = 0
) : Parcelable