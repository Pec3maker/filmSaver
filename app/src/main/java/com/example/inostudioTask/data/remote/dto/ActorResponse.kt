package com.example.inostudioTask.data.remote.dto

import com.squareup.moshi.Json

data class ActorResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "known_for")
    val knownFor: List<Film>,
    @Json(name = "name")
    val name: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String
)