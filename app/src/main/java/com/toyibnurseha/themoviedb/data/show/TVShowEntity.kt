package com.toyibnurseha.themoviedb.data.show

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.toyibnurseha.themoviedb.utils.Constant.SHOW_TABLE_NAME


data class TVShowEntity(
    val backdrop_path: String? = null,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null
)