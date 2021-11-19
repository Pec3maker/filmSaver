package com.example.inostudioTask.domain.repository


import com.example.inostudioTask.data.remote.dto.FilmResult
import kotlinx.coroutines.flow.Flow

interface FilmRepository {

    suspend fun getFilms(apiKey: String, page: Int, language: String): List<FilmResult>
    suspend fun getFilmsById(apiKey: String, id: String, language: String): FilmResult
    suspend fun getFilmsBySearch(
        apiKey: String,
        query: String,
        page: Int,
        language: String
    ): List<FilmResult>

    fun getFilmsByIdUseCase(
        apiKey: String,
        id: String,
        language: String
    ): Flow<Any>

    fun getFilmsUseCase(
        apiKey: String,
        page: Int,
        language: String
    ): Flow<Any>

    fun getFilmsBySearchUseCase(
        apiKey: String,
        page: Int,
        query: String,
        language: String
    ): Flow<Any>
}