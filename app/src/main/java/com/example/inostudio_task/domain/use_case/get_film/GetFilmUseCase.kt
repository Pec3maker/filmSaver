package com.example.inostudio_task.domain.use_case.get_film

import com.example.inostudio_task.common.Resource
import com.example.inostudio_task.data.remote.dto.toFilm
import com.example.inostudio_task.domain.model.Film
import com.example.inostudio_task.domain.repository.FilmRepository
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