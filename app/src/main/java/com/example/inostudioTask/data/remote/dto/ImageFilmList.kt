package com.example.inostudioTask.data.remote.dto

data class ImageFilmList(
    val backdrops: List<ImageResponse>,
    val posters: List<ImageResponse>
)

fun ImageFilmList.toCombinedImages(): List<ImageResponse> {
    return backdrops.plus(posters)
}