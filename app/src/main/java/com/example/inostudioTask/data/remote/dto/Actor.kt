package com.example.inostudioTask.data.remote.dto

import com.example.inostudioTask.data.dataSource.dto.ActorEntity
import com.squareup.moshi.Json

data class Actor(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String,
    val isInDatabase: Boolean? = null
)

fun Actor.toActorEntity(): ActorEntity {
    return ActorEntity(
        id = id,
        name = name,
        popularity = popularity,
        profilePath = profilePath
    )
}