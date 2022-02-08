package com.example.inostudioTask.data.remote.dto

data class ImageFilmList(
    val backdrops: List<ImageResponse>,
    val posters: List<ImageResponse>
) {
    fun toCombinedImages(): List<ImageResponse> = backdrops.plus(posters)
}