package com.example.inostudioTask.presentation.filmList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.dto.Film
import com.example.inostudioTask.domain.repository.FilmRepository
import com.example.inostudioTask.presentation.common.ListState
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

    private val _state = mutableStateOf<ListState<Film>>(ListState.Empty)
    private var coroutineJob: Job = Job()

    val state: State<ListState<Film>> = _state
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
            getFilms()
        } else {
            getFilmsBySearch(query = searchTextState.value)
        }
    }

    fun onFavoriteClick(film: Film) {
        viewModelScope.launch {
            repository.onFavoriteClick(film = film)
        }
    }

    private fun onDatabaseUpdate() {
        viewModelScope.launch {
            repository.filmListFlow.collect {
                val filmListState = _state.value
                if (filmListState is ListState.Success) {
                    _state.value = fillFilmsAccessory(filmListState.data)
                }
            }
        }
    }

    private fun getFilms() {
        coroutineJob.cancel()
        coroutineJob = viewModelScope.launch {
            progressBarState.value = true
            try {
                val data = repository.getFilms(
                    apiKey = Constants.API_KEY,
                    page = Constants.SEARCH_PAGE,
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
        if (_state.value !is ListState.Success) {
            _state.value = ListState.Error(message = e.message ?: "")
        } else {
            errorMessage.emit(e.message ?: "")
        }
    }

    private fun getFilmsBySearch(query: String) {
        coroutineJob.cancel()
        coroutineJob = viewModelScope.launch {
            progressBarState.value = true
            try {
                val data = repository.getFilmsBySearch(
                    apiKey = Constants.API_KEY,
                    page = Constants.SEARCH_PAGE,
                    query = query,
                    language = Constants.LANGUAGE
                )
                if (data.isEmpty()) {
                    _state.value = ListState.Empty
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

    private fun fillFilmsAccessory(filmList: List<Film>): ListState.Success<Film> {
        val changedFilmList = filmList.toMutableList().apply {
            replaceAll { film ->
                film.copy(isInDatabase = repository.filmListFlow.value.any { it.id == film.id })
            }
        }
        return ListState.Success(changedFilmList)
    }
}