package com.example.inostudioTask.data.remote.dto


import com.squareup.moshi.Json


data class Image(
    @Json(name = "file_path")
    val filePath: String,
    val height: Int,
    val width: Int
)