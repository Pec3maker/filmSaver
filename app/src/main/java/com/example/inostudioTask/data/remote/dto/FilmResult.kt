package com.example.inostudioTask.data.remote.dto


import com.example.inostudioTask.domain.model.Film
import com.squareup.moshi.Json


data class FilmResult(
    val adult: Boolean?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    val id: Int?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Int?
)

fun FilmResult.toFilm(): Film {
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