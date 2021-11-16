package com.example.inostudioTask.domain.useCase.getFilm

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

class GetFilmUseCase @Inject constructor(
    private val repository: FilmRepository,
    @ApplicationContext private val context: Context
){
    operator fun invoke(
        apiKey: String,
        id: String,
        language: String
    ): Flow<Resource<Film>> = flow {
        try{
            emit(Resource.Loading<Film>())
            val film = repository.getFilmsById(
                apiKey = apiKey,
                id = id,
                language = language
            ).toFilm()
            emit(Resource.Success<Film>(film))
        } catch (e: HttpException) {
            emit(Resource.Error<Film>(
                    e.localizedMessage?: context.getString(R.string.unexpected_error))
            )
        } catch (e: IOException) {
            emit(Resource.Error<Film>(
                context.getString(R.string.connection_error))
            )
        }
    }
}