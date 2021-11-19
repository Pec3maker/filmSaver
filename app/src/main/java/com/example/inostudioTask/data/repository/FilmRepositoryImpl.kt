package com.example.inostudioTask.data.repository

import com.example.inostudioTask.R
import com.example.inostudioTask.common.Resource
import com.example.inostudioTask.data.remote.FilmApi
import com.example.inostudioTask.data.remote.dto.FilmResult
import com.example.inostudioTask.data.remote.dto.toFilm
import com.example.inostudioTask.domain.model.Film
import com.example.inostudioTask.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val api: FilmApi
) : FilmRepository {

    override suspend fun getFilms(apiKey: String, page: Int, language: String): List<FilmResult> {
        return api.getFilms(apiKey = apiKey, page = page, language = language).results
    }

    override suspend fun getFilmsById(apiKey: String, id: String, language: String): FilmResult {
        return api.getFilmsById(apiKey = apiKey, filmId = id, language = language)
    }

    override suspend fun getFilmsBySearch(
        apiKey: String,
        query: String,
        page: Int,
        language: String
    ): List<FilmResult> {
        return api.getFilmsBySearch(
            apiKey = apiKey,
            query = query,
            page = page,
            language = language
        ).results
    }


    override fun getFilmsByIdUseCase(
        apiKey: String,
        id: String,
        language: String
    ): Flow<Any> = flow {
        try{
            emit(Resource.Loading<Film>())
            val film = getFilmsById(
                apiKey = apiKey,
                id = id,
                language = language
            ).toFilm()
            emit(Resource.Success(film))
        } catch (e: HttpException) {
            emit(Resource.Error(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error(R.string.connection_error))
        }
    }

    override fun getFilmsUseCase(
        apiKey: String,
        page: Int,
        language: String
    ): Flow<Any> = flow {
        try{
            emit(Resource.Loading<List<Film>>())
            val films = getFilms(
                apiKey = apiKey,
                page = page,
                language = language
            ).map { it.toFilm() }
            emit(Resource.Success(films))
        } catch (e: HttpException) {
            emit(Resource.Error(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error(R.string.connection_error))
        }
    }

    override fun getFilmsBySearchUseCase(
        apiKey: String,
        page: Int,
        query: String,
        language: String
    ): Flow<Any> = flow {
        try{
            emit(Resource.Loading<List<Film>>())
            val films = getFilmsBySearch(
                apiKey = apiKey,
                page = page,
                query = query,
                language = language
            ).map { it.toFilm() }
            emit(Resource.Success(films))
        } catch (e: HttpException) {
            emit(Resource.Error(R.string.unexpected_error))
        } catch (e: IOException) {
            emit(Resource.Error(R.string.connection_error))
        }
    }

}