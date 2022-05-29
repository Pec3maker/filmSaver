package com.example.inostudioTask.data.remote.dto

import com.squareup.moshi.Json

data class ActorsList(
    @Json(name = "results")
    val results: List<Actor>,
)