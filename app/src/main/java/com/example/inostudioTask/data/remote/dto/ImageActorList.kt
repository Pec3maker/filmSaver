package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.common.Constants
import com.squareup.moshi.Json

data class ImageActorList(
    @Json(name = "profiles")
    val images: List<ImageResponse>
)

fun ImageActorList.url(id: Int): String {
    return Constants.IMAGE_PATH.plus(images[id].filePath)
}
