package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.domain.model.dataBase.FilmEntity
import com.squareup.moshi.Json

data class Film(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    val id: Int,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    val title: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    val credits: CreditList? = null,
    val images: ImageList? = null,
    val reviews: ReviewList? = null,
    val videos: VideoList? = null,
    var isInDatabase: Boolean? = null
)

fun Film.toFilmEntity(): FilmEntity {
    return FilmEntity(
        id = id,
        title = title,
        originalTitle = originalTitle,
        posterPath = posterPath ?: "",
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}