package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.domain.model.CombinedImages


data class ImageList(
    val backdrops: List<Image>,
    val posters: List<Image>
)

fun ImageList.toCombinedImages(): CombinedImages {
    return CombinedImages(
        images = backdrops.plus(posters)
    )
}
