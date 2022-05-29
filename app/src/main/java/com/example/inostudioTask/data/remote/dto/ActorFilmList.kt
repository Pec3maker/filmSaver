package com.example.inostudioTask.data.remote.dto

import com.squareup.moshi.Json

data class ActorFilmList(
    @Json(name = "cast")
    val results: List<Film>
)