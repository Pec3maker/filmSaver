package com.example.inostudioTask.domain.repository


import com.example.inostudioTask.data.remote.dto.FilmResult

interface FilmRepository {

    suspend fun getFilms(apiKey: String, page: Int, language: String): List<FilmResult>
    suspend fun getFilmsById(apiKey: String, id: String, language: String): FilmResult
    suspend fun getFilmsBySearch(
        apiKey: String,
        query: String,
        page: Int,
        language: String
    ): List<FilmResult>
}