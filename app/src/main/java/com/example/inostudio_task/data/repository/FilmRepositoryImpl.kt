package com.example.inostudio_task.data.repository

import com.example.inostudio_task.data.remote.FilmApi
import com.example.inostudio_task.data.remote.dto.FilmDetailDto
import com.example.inostudio_task.data.remote.dto.Result
import com.example.inostudio_task.domain.repository.FilmRepository
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val api: FilmApi
) : FilmRepository {

    override suspend fun getFilms(apiKey: String, page: Int, language: String): List<Result> {
        return api.getFilms(apiKey = apiKey, page = page, language = language).results
    }

    override suspend fun getFilmsById(apiKey: String, id: String, language: String): FilmDetailDto {
        return api.getFilmsById(apiKey = apiKey, filmId = id, language = language)
    }

    override suspend fun getFilmsBySearch(apiKey: String, query: String, page: Int, language: String): List<Result> {
        return api.getFilmsBySearch(apiKey = apiKey, query = query, page = page, language = language).results
    }
}