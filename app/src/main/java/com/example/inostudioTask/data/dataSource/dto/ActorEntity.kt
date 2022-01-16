package com.example.inostudioTask.data.dataSource.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.inostudioTask.data.remote.dto.Actor

@Entity
data class ActorEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val popularity: Double,
    val profilePath: String
)

fun ActorEntity.toActor(): Actor {
    return Actor(
        id = id,
        name = name,
        popularity = popularity,
        profilePath = profilePath,
        isInDatabase = true
    )
}