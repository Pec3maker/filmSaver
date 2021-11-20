package com.example.inostudioTask.data.remote

import com.example.inostudioTask.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {
    @GET("/3/discover/movie")
    suspend fun getFilms(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): FilmList

    @GET("/3/search/movie")
    suspend fun getFilmsBySearch(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): FilmList

    @GET("/3/movie/{movie_id}")
    suspend fun getFilmsById(
        @Path("movie_id") filmId: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): FilmResponse

    @GET("/3/movie/{movie_id}")
    suspend fun getAdditionalInfo(
        @Path("movie_id") filmId: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("append_to_response") additionalInfo: String
    ): AdditionalInfoResponse
}