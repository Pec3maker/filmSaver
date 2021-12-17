package com.example.inostudioTask.data.repository

import com.example.inostudioTask.data.dataSource.FilmDao
import com.example.inostudioTask.data.remote.FilmApi
import com.example.inostudioTask.data.remote.dto.*
import com.example.inostudioTask.domain.model.dataBase.FilmEntity
import com.example.inostudioTask.domain.repository.FilmRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FilmRepositoryImpl @Inject constructor(
    private val api: FilmApi,
    private val dao: FilmDao
) : FilmRepository {

    private val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()
    private val scope: CoroutineScope = CoroutineScope(coroutineContext)

    override val updateDatabaseFlow = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        scope.launch {
            getFilmsDatabase().collect { films ->
                filmListDatabase = films
                updateDatabaseFlow.emit(Unit)
            }
        }
    }

    override var filmListDatabase = emptyList<FilmEntity>()

    private fun getFilmsDatabase(): Flow<List<FilmEntity>> {
        return dao.getFilms()
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

    override suspend fun insertFilmDatabase(film: FilmEntity) {
        dao.insertFilm(film = film)
    }

    override suspend fun deleteFilmDatabase(film: FilmEntity) {
        dao.deleteFilm(film = film)
    }

    override suspend fun getFilmsByIdDatabase(id: Int): FilmEntity? {
        return dao.getFilmsById(id)
    }
}