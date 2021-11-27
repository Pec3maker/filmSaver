package com.example.inostudioTask.data.remote.dto


data class ImageList(
    val backdrops: List<ImageResponse>,
    val posters: List<ImageResponse>
)

fun ImageList.toCombinedImages(): List<ImageResponse> {
    return backdrops.plus(posters)
}