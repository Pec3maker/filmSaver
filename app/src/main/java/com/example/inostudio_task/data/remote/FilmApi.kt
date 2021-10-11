package com.example.inostudio_task.data.remote

import com.example.inostudio_task.data.remote.dto.FilmDetailDto
import com.example.inostudio_task.data.remote.dto.FilmDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {
    @GET("/3/discover/movie")
    suspend fun getFilms(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): FilmDto

    @GET("/3/search/movie")
    suspend fun getFilmsBySearch(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): FilmDto

    @GET("/3/movie/{movie_id}")
    suspend fun getFilmsById(
        @Path("movie_id") filmId: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): FilmDetailDto
}