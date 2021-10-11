package com.example.inostudio_task.data.remote.dto


import com.google.gson.annotations.SerializedName

data class FilmDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)