package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.common.Constants
import com.squareup.moshi.Json

data class ImageResponse(
    @Json(name = "file_path")
    val filePath: String,
    val height: Int,
    val width: Int
)

fun ImageResponse.url(): String {
    return Constants.IMAGE_PATH.plus(filePath)
}