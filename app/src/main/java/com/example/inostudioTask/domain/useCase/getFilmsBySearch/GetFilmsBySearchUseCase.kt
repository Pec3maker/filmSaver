package com.example.inostudioTask.domain.useCase.getFilmsBySearch

import com.example.inostudioTask.common.Resource
import com.example.inostudioTask.data.remote.dto.toFilm
import com.example.inostudioTask.domain.model.Film
import com.example.inostudioTask.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFilmsBySearchUseCase @Inject constructor(
    private val repository: FilmRepository
){
    operator fun invoke(apiKey: String, page: Int, query: String, language: String): Flow<Resource<List<Film>>> = flow {
        try{
            emit(Resource.Loading<List<Film>>())
            val films = repository.getFilmsBySearch(apiKey = apiKey, page = page, query = query, language = language).map { it.toFilm() }
            emit(Resource.Success<List<Film>>(films))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Film>>(e.localizedMessage?: "Unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Film>>("Couldn't reach server"))
        }
    }
}