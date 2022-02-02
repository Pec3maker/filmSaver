package com.example.inostudioTask.domain.repository

import com.example.inostudioTask.data.dataSource.ActorDao
import com.example.inostudioTask.data.dataSource.FilmDao
import com.example.inostudioTask.data.dataSource.dto.ActorEntity
import com.example.inostudioTask.data.dataSource.dto.FilmEntity
import com.example.inostudioTask.data.remote.FilmApi
import com.example.inostudioTask.data.remote.dto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    private fun getFilmsDatabase(): Flow<List<FilmEntity>> = filmDao.getFilms()

    private fun getActorsDatabase(): Flow<List<ActorEntity>> = actorDao.getActors()

    override suspend fun getFilms(
        apiKey: String,
        page: Int,
        language: String
    ): List<Film> =
        api.getFilms(
            apiKey = apiKey,
            page = page,
            language = language
        ).results

    override suspend fun getFilmsById(
        apiKey: String,
        id: String,
        language: String,
        additionalInfo: String
    ): Film =
        api.getAdditionalInfo(
            apiKey = apiKey,
            filmId = id,
            language = language,
            additionalInfo = additionalInfo
        )

    override suspend fun getFilmsBySearch(
        apiKey: String,
        query: String,
        page: Int,
        language: String
    ): List<Film> =
        api.getFilmsBySearch(
            apiKey = apiKey,
            query = query,
            page = page,
            language = language
        ).results

    override suspend fun getReviewList(
        apiKey: String,
        id: String,
        page: Int,
        language: String
    ): List<ReviewResponse> =
        api.getReviewList(
            apiKey = apiKey,
            filmId = id,
            page = page,
            language = language
        ).results

    override suspend fun getActorsList(
        apiKey: String,
        page: Int,
        language: String
    ): List<Actor> =
        api.getPopularActors(
            apiKey = apiKey,
            page = page,
            language = language
        ).results

    override suspend fun getActorDetails(
        apiKey: String,
        personId: String,
        language: String,
        additionalInfo: String
    ): Actor =
        api.getActorDetails(
            apiKey = apiKey,
            personId = personId,
            language = language,
            additionalInfo = additionalInfo
        )

    override suspend fun getFilmByIdDatabase(id: Int): FilmEntity? = filmDao.getFilmsById(id)

    override suspend fun getActorByIdDatabase(id: Int): ActorEntity? = actorDao.getActorById(id)

    override suspend fun onFavoriteClick(actor: Actor) =
        if (actor.isInDatabase!!) {
            actorDao.deleteActor(actor.toActorEntity())
        } else {
            actorDao.insertActor(actor.toActorEntity())
        }

    override suspend fun onFavoriteClick(film: Film) =
        if (film.isInDatabase!!) {
            filmDao.deleteFilm(film.toFilmEntity())
        } else {
            filmDao.insertFilm(film.toFilmEntity())
        }
}