package com.example.inostudioTask.domain.model

import com.example.inostudioTask.data.remote.dto.CreditList
import com.example.inostudioTask.data.remote.dto.ImageResponse
import com.example.inostudioTask.data.remote.dto.ReviewList
import com.example.inostudioTask.data.remote.dto.VideoList
import com.example.inostudioTask.domain.model.dataBase.FilmEntity

data class Film(
    val backdropPath: String?,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String,
    val voteAverage: Double,
    val credits: CreditList? = null,
    val images: List<ImageResponse>? = null,
    val reviews: ReviewList? = null,
    val videos: VideoList? = null,
    var isInDatabase: Boolean? = null
)

fun Film.toFilmEntity(): FilmEntity {
    return FilmEntity(
        id = id,
        title = title,
        originalTitle = originalTitle,
        posterPath = posterPath?: "",
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}
