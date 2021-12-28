package com.example.inostudioTask.data.dataSource

import androidx.room.*
import com.example.inostudioTask.data.dataSource.dto.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao  {

    @Query("SELECT * FROM FilmEntity")
    fun getFilms(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM FilmEntity WHERE id = :id")
    suspend fun getFilmsById(id: Int): FilmEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: FilmEntity)

    @Delete
    suspend fun deleteFilm(film: FilmEntity)
}