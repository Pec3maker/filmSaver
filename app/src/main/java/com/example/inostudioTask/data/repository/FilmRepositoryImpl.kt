package com.example.inostudioTask.data.repository

import com.example.inostudioTask.data.remote.FilmApi
import com.example.inostudioTask.data.remote.dto.*
import com.example.inostudioTask.domain.repository.FilmRepository
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val api: FilmApi
) : FilmRepository {

    override suspend fun getFilms(apiKey: String, page: Int, language: String): List<FilmResponse> {
        return api.getFilms(apiKey = apiKey, page = page, language = language).results
    }

    override suspend fun getFilmsById(apiKey: String, id: String, language: String): FilmResponse {
        return api.getFilmsById(apiKey = apiKey, filmId = id, language = language)
    }

    override suspend fun getFilmsBySearch(
        apiKey: String,
        query: String,
        page: Int,
        language: String
    ): List<FilmResponse> {
        return api.getFilmsBySearch(
            apiKey = apiKey,
            query = query,
            page = page,
            language = language
        ).results
    }

    override suspend fun getCast(apiKey: String, id: String, language: String): List<Cast> {
        return api.getCast(apiKey = apiKey, filmId = id, language = language).cast
    }

    override suspend fun getImages(apiKey: String, id: String): List<Image> {
        return api.getImages(apiKey = apiKey, filmId = id).toCombinedImages().images
    }

    override suspend fun getReviews(apiKey: String, id: String): List<ReviewResponse> {
        return api.getReviews(apiKey = apiKey, filmId = id).results
    }

    override suspend fun getVideos(apiKey: String, id: String): List<VideoResponse> {
        return api.getVideos(apiKey = apiKey, filmId = id).results
    }
}