package com.example.inostudioTask.presentation.filmList

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.R
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.common.Resource
import com.example.inostudioTask.data.remote.dto.toFilm
import com.example.inostudioTask.domain.model.Film
import com.example.inostudioTask.domain.repository.FilmRepository
import com.example.inostudioTask.presentation.screenStates.ScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val repository: FilmRepository,
) : ViewModel() {

    private val _state = mutableStateOf(ScreenStates.FilmListState<Any>())
    val state: State<ScreenStates.FilmListState<Any>> = _state


    init {
        refresh()
    }


    private fun getFilms(page: Int) {

        flow {
            try {
                emit(Resource.Loading<List<Film>>())
                val film = repository.getFilms(
                    apiKey = Constants.API_KEY,
                    page = page,
                    language = Constants.LANGUAGE
                ).map { it.toFilm() }
                emit(Resource.Success(film))
            } catch (e: HttpException) {
                emit(Resource.Error(R.string.unexpected_error))
            } catch (e: IOException) {
                emit(Resource.Error(R.string.connection_error))
            }
        }.onEach { result ->
            when (result) {
                is Resource.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    _state.value = ScreenStates.FilmListState(
                        data = _state.value.data.plus(result.data as List<Film>)
                    )
                }
                is Resource.Error<*> -> {
                    _state.value = ScreenStates.FilmListState(
                        error = R.string.unexpected_error
                    )
                }
                is Resource.Loading<*> -> {
                    _state.value = ScreenStates.FilmListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getFilmsBySearch(page: Int, query: String) {

        flow {
            try {
                emit(Resource.Loading<List<Film>>())
                val film = repository.getFilmsBySearch(
                    apiKey = Constants.API_KEY,
                    page = page,
                    query = query,
                    language = Constants.LANGUAGE
                ).map { it.toFilm() }
                emit(Resource.Success(film))
            } catch (e: HttpException) {
                emit(Resource.Error(R.string.unexpected_error))
            } catch (e: IOException) {
                emit(Resource.Error(R.string.connection_error))
            }
        }.onEach { result ->
            when (result) {
                is Resource.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    _state.value = ScreenStates.FilmListState(
                        data = _state.value.data.plus(result.data as List<Film>)
                    )
                }
                is Resource.Error<*> -> {
                    _state.value = ScreenStates.FilmListState(
                        error = R.string.unexpected_error
                    )
                }
                is Resource.Loading<*> -> {
                    _state.value = ScreenStates.FilmListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refresh() {
        for (i in 1..10) {
            getFilms(page = i)
        }
    }

    fun searchFilms(query: String) {
        getFilmsBySearch(page = Constants.SEARCH_PAGES, query = query)
    }
}