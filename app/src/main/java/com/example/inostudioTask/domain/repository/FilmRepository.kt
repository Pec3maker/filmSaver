package com.example.inostudioTask.domain.repository

import com.example.inostudioTask.data.remote.dto.*
import com.example.inostudioTask.domain.model.dataBase.FilmEntity
import kotlinx.coroutines.flow.Flow

interface FilmRepository {

    suspend fun getFilms(
        apiKey: String,
        page: Int,
        language: String
    ): List<Film>

    suspend fun getFilmsById(
        apiKey: String,
        id: String,
        language: String,
        additionalInfo: String
    ): Film

    suspend fun getFilmsBySearch(
        apiKey: String,
        query: String,
        page: Int,
        language: String
    ): List<Film>

    fun getFilmsDatabase(): Flow<List<FilmEntity>>

    suspend fun insertFilmDatabase(film: FilmEntity)

    suspend fun deleteFilmDatabase(film: FilmEntity)

    suspend fun getFilmsByIdDatabase(id: Int): FilmEntity?
}