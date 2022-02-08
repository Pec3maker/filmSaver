package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.common.FilmRepository.Companion.IMAGE_PATH
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

    fun imageUrl(image: String): String = IMAGE_PATH.plus(image)

    fun toFilmEntity(): FilmEntity =
        FilmEntity(
            id = id,
            title = title,
            originalTitle = originalTitle,
            posterPath = posterPath ?: "",
            overview = overview,
            releaseDate = releaseDate,
            voteAverage = voteAverage
        )

    fun videoUrl(): String =
        if (videos != null) {
            BASE_YOUTUBE_URL.plus(videos.results[0].key)
        } else {
            ""
        }

    fun posterPathUrl(): String = IMAGE_PATH.plus(posterPath)

    companion object {
        const val BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v="
    }
}