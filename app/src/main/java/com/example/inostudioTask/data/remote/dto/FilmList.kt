package com.example.inostudioTask.data.remote.dto


import com.squareup.moshi.Json


data class FilmList(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<FilmResult>,
    @Json(name = "total_pages") val totalPages: Int?,
    @Json(name = "total_results") val totalResults: Int?
)
