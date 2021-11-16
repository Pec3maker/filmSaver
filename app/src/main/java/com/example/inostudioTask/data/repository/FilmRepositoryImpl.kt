package com.example.inostudioTask.data.repository

import com.example.inostudioTask.data.remote.FilmApi
import com.example.inostudioTask.data.remote.dto.FilmDetailDto
import com.example.inostudioTask.data.remote.dto.Result
import com.example.inostudioTask.domain.repository.FilmRepository
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