package com.example.inostudioTask.data.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inostudioTask.domain.model.dataBase.FilmEntity

@Database(
    entities = [FilmEntity::class],
    version = 2
)
abstract class FilmDatabase: RoomDatabase() {

    abstract val filmDao: FilmDao
}