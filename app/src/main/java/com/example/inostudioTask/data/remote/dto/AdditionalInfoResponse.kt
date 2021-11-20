package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.domain.model.Film
import com.squareup.moshi.Json

data class AdditionalInfoResponse(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    val id: Int,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    val title: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    val credits: CreditList,
    val images: ImageList,
    val reviews: ReviewList,
    val videos: VideoList,
)

fun AdditionalInfoResponse.toFilm(): Film {
    return Film(
        backdropPath = backdropPath,
        id = id,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        credits = credits,
        images = images.toCombinedImages(),
        reviews = reviews,
        videos = videos
    )
}