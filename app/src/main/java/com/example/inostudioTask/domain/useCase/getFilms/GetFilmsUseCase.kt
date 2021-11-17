package com.example.inostudioTask.domain.useCase.getFilms

import android.content.Context
import com.example.inostudioTask.R
import com.example.inostudioTask.common.Resource
import com.example.inostudioTask.data.remote.dto.toFilm
import com.example.inostudioTask.domain.model.Film
import com.example.inostudioTask.domain.repository.FilmRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFilmsUseCase @Inject constructor(
    private val repository: FilmRepository,
    @ApplicationContext private val context: Context
){
    operator fun invoke(
        apiKey: String,
        page: Int,
        language: String
    ): Flow<Resource<List<Film>>> = flow {
        try{
            emit(Resource.Loading<List<Film>>())
            val films = repository.getFilms(
                apiKey = apiKey,
                page = page,
                language = language
            ).map { it.toFilm() }
            emit(Resource.Success<List<Film>>(films))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Film>>(
                e.localizedMessage?: context.getString(R.string.unexpected_error))
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<Film>>(
                context.getString(R.string.connection_error))
            )
        }
    }
}