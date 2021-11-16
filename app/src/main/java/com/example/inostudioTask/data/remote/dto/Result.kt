package com.example.inostudioTask.data.remote.dto


import com.example.inostudioTask.domain.model.Film
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)

fun Result.toFilm(): Film {
    return Film(
        id = id,
        backdropPath = backdropPath,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage
    )
}