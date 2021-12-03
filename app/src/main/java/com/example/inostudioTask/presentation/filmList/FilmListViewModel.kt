package com.example.inostudioTask.presentation.filmList

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.data.remote.dto.toFilmEntity
import com.example.inostudioTask.domain.model.dataBase.FilmEntity
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
    private var filmListDatabase = emptyList<FilmEntity>()
    val searchText = mutableStateOf("")

    init {
        getFilmsDatabase()
        refresh()
    }

    fun refresh() {
        getFilms(page = Constants.SEARCH_PAGE)
    }

    fun searchFilms(text: String) {
        searchText.value = text
        if (searchText.value.isEmpty()) {
            refresh()
        } else {
            getFilmsBySearch(page = Constants.SEARCH_PAGE, query = searchText.value)
        }
    }

    fun addFavorite(film: Film) {
        if (film.isInDatabase!!) {
            deleteFilm(film = film)
        } else {
            saveFilm(film = film)
        }
    }

    private fun getFilms(page: Int) {
        viewModelScope.launch {
            try {
                _state.value = FilmListState.Loading
                val data = repository.getFilms(
                    apiKey = Constants.API_KEY,
                    page = page,
                    language = Constants.LANGUAGE
                )
                _state.value = fillFilmAccessory(data)
            } catch (e: HttpException) {
                _state.value = FilmListState.Error(
                    exception = e.message
                )
            } catch (e: IOException) {
                _state.value = FilmListState.Error(
                    exception = e.message
                )
            }
        }
    }

    private fun getFilmsBySearch(page: Int, query: String) {
        viewModelScope.launch {
            try {
                _state.value = FilmListState.Loading
                val data = repository.getFilmsBySearch(
                    apiKey = Constants.API_KEY,
                    page = page,
                    query = query,
                    language = Constants.LANGUAGE
                )
                if (data.isEmpty()) {
                    _state.value = FilmListState.Empty
                } else {
                    _state.value = fillFilmAccessory(data)
                }
            } catch (e: HttpException) {
                _state.value = FilmListState.Error(
                    exception = e.message
                )
            } catch (e: IOException) {
                _state.value = FilmListState.Error(
                    exception = e.message
                )
            }
        }
    }

    private fun getFilmsDatabase() {
        repository.getFilmsDatabase().onEach { films ->
            filmListDatabase = films
            val currentState = _state.value
            if (currentState is FilmListState.Success) {
                _state.value = fillFilmAccessory(currentState.data)
            }
        }.launchIn(viewModelScope)
    }

    private fun saveFilm(film: Film) {
        viewModelScope.launch {
            repository.insertFilmDatabase(film.toFilmEntity())
        }
    }

    private fun deleteFilm(film: Film) {
        viewModelScope.launch {
            repository.deleteFilmDatabase(film.toFilmEntity())
        }
    }

    private fun fillFilmAccessory(filmList: List<Film>): FilmListState.Success<Film>{
        val changedFilmList = filmList.toMutableList().apply {
            replaceAll { film ->
                film.copy(isInDatabase = filmListDatabase.any { it.id == film.id })
            }
        }
        return FilmListState.Success(changedFilmList)
    }
}