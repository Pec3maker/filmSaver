package com.example.inostudioTask.common

import com.example.inostudioTask.data.dataSource.dto.ActorEntity
import com.example.inostudioTask.data.dataSource.dto.FilmEntity
import com.example.inostudioTask.data.remote.dto.Actor
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.data.remote.dto.ReviewResponse
import kotlinx.coroutines.flow.StateFlow

interface FilmRepository {

    val filmListFlow: StateFlow<List<FilmEntity>>
    val actorListFlow: StateFlow<List<ActorEntity>>

    suspend fun getFilms(): List<Film>

    suspend fun getFilmsById(id: String): Film

    suspend fun getFilmsBySearch(query: String): List<Film>

    suspend fun getReviewList(id: String): List<ReviewResponse>

    suspend fun getActorsList(): List<Actor>

    suspend fun getActorDetails(personId: String): Actor

    suspend fun getFilmByIdDatabase(id: Int): FilmEntity?

    suspend fun getActorByIdDatabase(id: Int): ActorEntity?

    suspend fun onFavoriteClick(actor: Actor)

    suspend fun onFavoriteClick(film: Film)

    companion object {
        const val IMAGE_PATH = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "f1c1fa32aa618e6adc168c3cc3cc6c46"
        const val LANGUAGE = "en"
        const val SEARCH_PAGE = 1
        const val FILM_ADDITIONAL_INFO  = "videos,images,reviews,credits"
        const val ACTOR_ADDITIONAL_INFO  = "images,movie_credits"
    }
}