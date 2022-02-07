package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.dataSource.dto.FilmEntity
import com.squareup.moshi.Json

data class Film(
    @Json(name = "backdrop_path")
    val backdropPath: String? = null,
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
    val images: ImageFilmList? = null,
    val reviews: ReviewList? = null,
    val videos: VideoList? = null,
    val isInDatabase: Boolean? = null,
    val linesToShow: Int = 5
) {
    fun imageUrl(image: String): String = Constants.IMAGE_PATH.plus(image)
}


fun Film.toFilmEntity(): FilmEntity =
    FilmEntity(
        id = id,
        title = title,
        originalTitle = originalTitle,
        posterPath = posterPath ?: "",
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )

fun Film.videoUrl(): String =
    if (videos != null) {
        "https://www.youtube.com/watch?v=".plus(videos.results[0].key)
    } else {
        ""
    }

fun Film.posterPathUrl(): String = Constants.IMAGE_PATH.plus(posterPath)