package com.example.inostudioTask.data.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inostudioTask.data.dataSource.dto.ActorEntity
import com.example.inostudioTask.data.dataSource.dto.FilmEntity

@Database(
    entities = [FilmEntity::class, ActorEntity::class],
    version = 1
)

abstract class FilmDatabase: RoomDatabase() {

    abstract val filmDao: FilmDao
    abstract val actorDao: ActorDao
}