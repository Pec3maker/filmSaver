package com.example.inostudioTask.domain.model

import com.example.inostudioTask.data.remote.dto.CreditList
import com.example.inostudioTask.data.remote.dto.ReviewList
import com.example.inostudioTask.data.remote.dto.VideoList

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
    val images: CombinedImages? = null,
    val reviews: ReviewList? = null,
    val videos: VideoList? = null,
)
