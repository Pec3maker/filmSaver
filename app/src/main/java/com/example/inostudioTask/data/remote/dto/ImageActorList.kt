package com.example.inostudioTask.data.remote.dto

import com.squareup.moshi.Json

data class ImageActorList(
    @Json(name = "profiles")
    val images: List<ImageResponse>
)
