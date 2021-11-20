package com.example.inostudioTask.domain.repository


import com.example.inostudioTask.data.remote.dto.*
import com.example.inostudioTask.domain.model.AdditionalInfo

interface FilmRepository {

    suspend fun getFilms(
        apiKey: String,
        page: Int,
        language: String
    ): List<FilmResponse>

    suspend fun getFilmsById(
        apiKey: String,
        id: String,
        language: String
    ): FilmResponse

    suspend fun getFilmsBySearch(
        apiKey: String,
        query: String,
        page: Int,
        language: String
    ): List<FilmResponse>

    suspend fun getCast(
        apiKey: String,
        id: String,
        language: String,
        additionalInfo: String
    ): AdditionalInfo
}