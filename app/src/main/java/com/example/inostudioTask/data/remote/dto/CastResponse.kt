package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.common.FilmRepository.Companion.IMAGE_PATH
import com.squareup.moshi.Json

data class CastResponse(
    val character: String?,
    val gender: Int,
    val id: Int,
    val name: String?,
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "profile_path")
    val profilePath: String?
) {
    fun profileUrl(): String = IMAGE_PATH.plus(profilePath)
}