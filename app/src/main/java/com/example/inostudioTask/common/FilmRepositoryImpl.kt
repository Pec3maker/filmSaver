package com.example.inostudioTask.common

import com.example.inostudioTask.common.FilmRepository.Companion.ACTOR_ADDITIONAL_INFO
import com.example.inostudioTask.common.FilmRepository.Companion.API_KEY
import com.example.inostudioTask.common.FilmRepository.Companion.FILM_ADDITIONAL_INFO
import com.example.inostudioTask.common.FilmRepository.Companion.LANGUAGE
import com.example.inostudioTask.common.FilmRepository.Companion.SEARCH_PAGE
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

    override suspend fun getFilms(): List<Film> =
        api.getFilms(
            apiKey = API_KEY,
            page = SEARCH_PAGE,
            language = LANGUAGE
        ).results

    override suspend fun getFilmsById(id: String): Film =
        api.getAdditionalInfo(
            apiKey = API_KEY,
            filmId = id,
            language = LANGUAGE,
            additionalInfo = FILM_ADDITIONAL_INFO
        )

    override suspend fun getFilmsBySearch(
        query: String
    ): List<Film> =
        api.getFilmsBySearch(
            apiKey = API_KEY,
            query = query,
            page = SEARCH_PAGE,
            language = LANGUAGE
        ).results

    override suspend fun getReviewList(
        id: String
    ): List<ReviewResponse> =
        api.getReviewList(
            apiKey = API_KEY,
            filmId = id,
            page = SEARCH_PAGE,
            language = LANGUAGE
        ).results

    override suspend fun getActorsList(): List<Actor> =
        api.getPopularActors(
            apiKey = API_KEY,
            page = SEARCH_PAGE,
            language = LANGUAGE
        ).results

    override suspend fun getActorDetails(personId: String): Actor =
        api.getActorDetails(
            apiKey = API_KEY,
            personId = personId,
            language = LANGUAGE,
            additionalInfo = ACTOR_ADDITIONAL_INFO
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