package com.example.inostudioTask.domain.repository


import com.example.inostudioTask.data.remote.dto.*

interface FilmRepository {

    suspend fun getFilms(apiKey: String, page: Int, language: String): List<FilmResponse>
    suspend fun getFilmsById(apiKey: String, id: String, language: String): FilmResponse
    suspend fun getFilmsBySearch(
        apiKey: String,
        query: String,
        page: Int,
        language: String
    ): List<FilmResponse>
    suspend fun getCast(apiKey: String, id: String, language: String): List<Cast>
    suspend fun getImages(apiKey: String, id: String): List<Image>
    suspend fun getReviews(apiKey: String, id: String): List<ReviewResponse>
    suspend fun getVideos(apiKey: String, id: String): List<VideoResponse>
}