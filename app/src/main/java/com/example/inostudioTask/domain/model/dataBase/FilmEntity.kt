package com.example.inostudioTask.domain.model.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

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
