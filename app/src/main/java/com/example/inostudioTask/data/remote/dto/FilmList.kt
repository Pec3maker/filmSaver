package com.example.inostudioTask.data.remote.dto


import com.squareup.moshi.Json


data class FilmList(
    val page: Int?,
    val results: List<FilmResult>,
    @Json(name = "total_pages") val totalPages: Int?,
    @Json(name = "total_results") val totalResults: Int?
)
