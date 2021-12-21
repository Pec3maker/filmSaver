package com.example.inostudioTask.presentation.filmList

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.data.remote.dto.toFilmEntity
import com.example.inostudioTask.domain.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val repository: FilmRepository,
) : ViewModel() {

    private val _state = mutableStateOf<FilmListState<Film>>(FilmListState.Empty)
    private var coroutineJob: Job = Job()

    val uiState: State<FilmListState<Film>> = _state
    val searchTextState = mutableStateOf("")
    val progressBarState = mutableStateOf(true)
    val errorMessage = MutableSharedFlow<String>()

    init {
        onDatabaseUpdate()
        searchFilms()
    }

    fun onSearchTextUpdate(text: String) {
        searchTextState.value = text;
        searchFilms()
    }

    fun searchFilms() {
        if (searchTextState.value.isEmpty()) {
            getFilms(Constants.SEARCH_PAGE)
        } else {
            getFilmsBySearch(page = Constants.SEARCH_PAGE, query = searchTextState.value)
        }
    }

    fun addFavorite(film: Film) {
        if (film.isInDatabase!!) {
            deleteFilm(film = film)
        } else {
            saveFilm(film = film)
        }
    }

    private fun onDatabaseUpdate() {
        viewModelScope.launch {
            repository.updateDatabaseFlow.collect {
                val filmListState = _state.value
                if (filmListState is FilmListState.Success) {
                    _state.value = fillFilmsAccessory(filmListState.data)
                }
            }
        }
    }

    private fun getFilms(page: Int) {
        coroutineJob.cancel()
        coroutineJob = viewModelScope.launch {
            progressBarState.value = true
            try {
                val data = repository.getFilms(
                    apiKey = Constants.API_KEY,
                    page = page,
                    language = Constants.LANGUAGE
                )
                _state.value = fillFilmsAccessory(data)
            } catch (e: HttpException) {
                errorHandler(e)
            } catch (e: IOException) {
                errorHandler(e)
            }
            progressBarState.value = false
        }
    }

    private suspend fun errorHandler(e: Exception) {
        if (_state.value !is FilmListState.Success) {
            _state.value = FilmListState.Error(
                message = e.message
            )
        } else {
            errorMessage.emit(e.message?: "")
        }
    }

    private fun getFilmsBySearch(page: Int, query: String) {
        coroutineJob.cancel()
        coroutineJob = viewModelScope.launch {
            progressBarState.value = true
            try {
                val data = repository.getFilmsBySearch(
                    apiKey = Constants.API_KEY,
                    page = page,
                    query = query,
                    language = Constants.LANGUAGE
                )
                if (data.isEmpty()) {
                    _state.value = FilmListState.Empty
                } else {
                    _state.value = fillFilmsAccessory(data)
                }
            } catch (e: HttpException) {
                errorHandler(e)
            } catch (e: IOException) {
                errorHandler(e)
            }
            progressBarState.value = false
        }
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

    private fun fillFilmsAccessory(filmList: List<Film>): FilmListState.Success<Film>{
        val changedFilmList = filmList.toMutableList().apply {
            replaceAll { film ->
                film.copy(isInDatabase = repository.filmListDatabase.any { it.id == film.id })
            }
        }
        return FilmListState.Success(changedFilmList)
    }
}