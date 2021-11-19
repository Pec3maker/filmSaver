package com.example.inostudioTask.domain.useCase.getFilm

import com.example.inostudioTask.common.Resource
import com.example.inostudioTask.data.remote.dto.toFilm
import com.example.inostudioTask.domain.model.Film
import com.example.inostudioTask.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFilmUseCase @Inject constructor(
    private val repository: FilmRepository
){
    operator fun invoke(apiKey: String, id: String, language: String): Flow<Resource<Film>> = flow {
        try{
            emit(Resource.Loading<Film>())
            val film = repository.getFilmsById(apiKey = apiKey, id = id, language = language).toFilm()
            emit(Resource.Success<Film>(film))
        } catch (e: HttpException) {
            emit(Resource.Error<Film>(e.localizedMessage?: "Unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<Film>("Couldn't reach server"))
        }
    }
}