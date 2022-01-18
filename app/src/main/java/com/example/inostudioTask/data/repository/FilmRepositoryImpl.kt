package com.example.inostudioTask.data.repository

import com.example.inostudioTask.data.dataSource.ActorDao
import com.example.inostudioTask.data.dataSource.FilmDao
import com.example.inostudioTask.data.dataSource.dto.ActorEntity
import com.example.inostudioTask.data.remote.FilmApi
import com.example.inostudioTask.data.remote.dto.*
import com.example.inostudioTask.data.dataSource.dto.FilmEntity
import com.example.inostudioTask.domain.repository.FilmRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FilmRepositoryImpl @Inject constructor(
    private val api: FilmApi,
    private val filmDao: FilmDao,
    private val actorDao: ActorDao
) : FilmRepository {

    private val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()
    private val scope: CoroutineScope = CoroutineScope(coroutineContext)

    override val filmListFlow = MutableStateFlow(emptyList<FilmEntity>())
    override val actorListFlow = MutableStateFlow(emptyList<ActorEntity>())

    init {
        scope.launch {
            getFilmsDatabase().collect { films ->
                filmListFlow.emit(films)
            }
        }

        scope.launch {
            getActorsDatabase().collect { actors ->
                actorListFlow.emit(actors)
            }
        }
    }

    private fun getFilmsDatabase(): Flow<List<FilmEntity>> {
        return filmDao.getFilms()
    }

    private fun getActorsDatabase(): Flow<List<ActorEntity>> {
        return actorDao.getActors()
    }

    override suspend fun getFilms(
        apiKey: String,
        page: Int,
        language: String
    ): List<Film> {
        return api.getFilms(
            apiKey = apiKey,
            page = page,
            language = language
        ).results
    }

    override suspend fun getFilmsById(
        apiKey: String,
        id: String,
        language: String,
        additionalInfo: String
    ): Film {
        return api.getAdditionalInfo(
            apiKey = apiKey,
            filmId = id,
            language = language,
            additionalInfo = additionalInfo
        )
    }

    override suspend fun getFilmsBySearch(
        apiKey: String,
        query: String,
        page: Int,
        language: String
    ): List<Film> {
        return api.getFilmsBySearch(
            apiKey = apiKey,
            query = query,
            page = page,
            language = language
        ).results
    }

    override suspend fun getReviewList(
        apiKey: String,
        id: String,
        page: Int,
        language: String
    ): List<ReviewResponse> {
        return api.getReviewList(
            apiKey = apiKey,
            filmId = id,
            page = page,
            language = language
        ).results
    }

    override suspend fun getActorsList(
        apiKey: String,
        page: Int,
        language: String
    ): List<Actor> {
        return api.getPopularActors(
            apiKey = apiKey,
            page = page,
            language = language
        ).results
    }

    override suspend fun getActorDetails(
        apiKey: String,
        personId: String,
        language: String,
        additionalInfo: String
    ): Actor {
        return api.getActorDetails(
            apiKey = apiKey,
            personId = personId,
            language = language,
            additionalInfo = additionalInfo
        )
    }

    override suspend fun insertFilmDatabase(film: FilmEntity) {
        filmDao.insertFilm(film = film)
    }

    override suspend fun deleteFilmDatabase(film: FilmEntity) {
        filmDao.deleteFilm(film = film)
    }

    override suspend fun getFilmsByIdDatabase(id: Int): FilmEntity? {
        return filmDao.getFilmsById(id = id)
    }

    override suspend fun insertActorDatabase(actor: ActorEntity) {
        actorDao.insertActor(actor = actor)
    }

    override suspend fun deleteActorDatabase(actor: ActorEntity) {
        actorDao.deleteActor(actor = actor)
    }

    override suspend fun getActorByIdDatabase(id: Int): ActorEntity? {
        return actorDao.getActorById(id)
    }
}