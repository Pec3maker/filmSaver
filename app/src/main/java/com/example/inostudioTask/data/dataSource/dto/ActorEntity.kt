package com.example.inostudioTask.data.dataSource.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActorEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val popularity: Double,
    val profilePath: String
)
