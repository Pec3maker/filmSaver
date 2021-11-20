package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.domain.model.CombinedImages


data class ImageList(
    val backdrops: List<ImageResponse>,
    val posters: List<ImageResponse>
)

fun ImageList.toCombinedImages(): CombinedImages {
    return CombinedImages(
        images = backdrops.plus(posters)
    )
}
