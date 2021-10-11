package com.example.inostudio_task.domain.use_case.get_films

import com.example.inostudio_task.common.Resource
import com.example.inostudio_task.data.remote.dto.toFilm
import com.example.inostudio_task.domain.model.Film
import com.example.inostudio_task.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFilmsUseCase @Inject constructor(
    private val repository: FilmRepository
){
    operator fun invoke(apiKey: String, page: Int, language: String): Flow<Resource<List<Film>>> = flow {
        try{
            emit(Resource.Loading<List<Film>>())
            val films = repository.getFilms(apiKey = apiKey, page = page, language = language).map { it.toFilm() }
            emit(Resource.Success<List<Film>>(films))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Film>>(e.localizedMessage?: "Unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Film>>("Couldn't reach server"))
        }
    }
}