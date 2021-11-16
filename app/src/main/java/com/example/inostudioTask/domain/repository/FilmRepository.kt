package com.example.inostudioTask.domain.repository

import com.example.inostudioTask.data.remote.dto.FilmDetailDto
import com.example.inostudioTask.data.remote.dto.Result

interface FilmRepository {

    suspend fun getFilms(apiKey: String, page: Int, language: String): List<Result>
    suspend fun getFilmsById(apiKey: String, id: String, language: String): FilmDetailDto
    suspend fun getFilmsBySearch(apiKey: String, query: String, page: Int, language: String): List<Result>
}