package com.example.inostudio_task.domain.model

data class Film(
    val backdropPath: String?,
    val id: Int?,
    val originalTitle: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val voteAverage: Double?,
    val images: List<String> = emptyList()
)