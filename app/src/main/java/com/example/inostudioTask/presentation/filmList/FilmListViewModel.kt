package com.example.inostudioTask.presentation.filmList

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.toFilm
import com.example.inostudioTask.domain.model.Film
import com.example.inostudioTask.domain.model.dataBase.FilmEntity
import com.example.inostudioTask.domain.model.toFilmEntity
import com.example.inostudioTask.domain.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val repository: FilmRepository,
) : ViewModel() {
    private val _state = mutableStateOf<FilmListState<Film>>(FilmListState.Empty)
    val state: State<FilmListState<Film>> = _state
    private val filmListDatabase = mutableStateOf(emptyList<FilmEntity>())
    val searchText = mutableStateOf("")

    init {
        getFilmsDatabase()
        refresh()
    }

    private fun getFilms(page: Int) {
        viewModelScope.launch {
            try {
                _state.value = FilmListState.Loading
                _state.value = FilmListState.Success(
                    data = repository.getFilms(
                        apiKey = Constants.api_key,
                        page = page,
                        language = Constants.language
                    ).map { it.toFilm() }
                )
            } catch (e: HttpException) {
                _state.value = FilmListState.Error(
                    exception = e
                )
            } catch (e: IOException) {
                _state.value = FilmListState.Error(
                    exception = e
                )
            }
        }
    }

    private fun getFilmsBySearch(page: Int, query: String) {
        viewModelScope.launch {
            try {
                _state.value = FilmListState.Loading
                _state.value = FilmListState.Success(
                    data = repository.getFilmsBySearch(
                        apiKey = Constants.api_key,
                        page = page,
                        query = query,
                        language = Constants.language
                    ).map { it.toFilm() }
                )
                if ((_state.value as FilmListState.Success<Film>).data.isEmpty()) {
                    _state.value = FilmListState.Empty
                }
            } catch (e: HttpException) {
                _state.value = FilmListState.Error(
                    exception = e
                )
            } catch (e: IOException) {
                _state.value = FilmListState.Error(
                    exception = e
                )
            }
        }
    }

    fun saveFilm(film: Film) {
        viewModelScope.launch {
            repository.insertFilmDatabase(film.toFilmEntity())
        }
        getFilmsDatabase()
    }

    private fun getFilmsDatabase() {
        repository.getFilmsDatabase().onEach {
            filmListDatabase.value = it
        }.launchIn(viewModelScope)
    }

    fun deleteFilm(film: Film) {
        viewModelScope.launch {
            repository.deleteFilmDatabase(film.toFilmEntity())
        }
        getFilmsDatabase()
    }

    fun refresh() {
        getFilms(page = Constants.search_pages)
    }

    fun searchFilms() {
        if (searchText.value.isEmpty()) {
            refresh()
        } else {
            getFilmsBySearch(page = Constants.search_pages, query = searchText.value)
        }
    }

    fun isContainFilm(film: Film): Boolean {
        return filmListDatabase.value.indexOf(film.toFilmEntity()) != -1
    }
}