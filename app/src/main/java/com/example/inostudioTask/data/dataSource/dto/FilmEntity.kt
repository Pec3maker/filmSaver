package com.example.inostudioTask.data.dataSource.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.inostudioTask.data.remote.dto.Film

@Entity
data class FilmEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val originalTitle: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String?,
    val voteAverage: Double
)

fun FilmEntity.toFilm(): Film {
    return Film(
        id = id,
        title = title,
        originalTitle = originalTitle,
        posterPath = posterPath,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        isInDatabase = true
    )
}